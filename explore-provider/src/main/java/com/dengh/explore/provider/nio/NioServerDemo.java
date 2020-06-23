package com.dengh.explore.provider.nio;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author dengH
 * @title: NioServerDemo
 * @description:
 * @date 2019/7/21 16:08
 *
 * 使用NIO来实现一个网络服务器：
 *
 * BIO：一个线程创建ServerSocket，死循环调用accept()获取新连接，一旦获取到新连接，创建对该socket的读写任务，
 *      并放入线程池中，socket的读是阻塞的，面对不活跃的连接，线程是不会释放；
 * 当面对高并发，上万连接，并且是不太活跃的长连接时，线程池会有上万任务，线程池无法创建上万线程来满足，
 * 造成服务器变慢，甚至无响应，内存占满；
 *
 * NIO：一个线程创建ServerSocketChannel，配置为非阻塞，创建一个Selector，将ServerSocketChannel注册到Selector,
 *      监听其accept事件，死循环调用selector的select()方法来获取发生注册事件的channel，每当发生注册事件，
 *      从ServerSocketChannel获取连接，注册到selector，监听器其read事件，当循环中发生read事件，创建相应的任务，
 *      放入线程池中；
 * 经过NIO的处理，在面对高并发，上万连接时，只是在有socket连接，有socket可读时才会创建任务，放入线程池，并且任务
 * 处理不会阻塞，这样大大减少了线程池中线程的个数，能支持更高的并发；
 *
 * 以上的逻辑有一个弊端：
 *      1. ServerSocketChannel等待连接的过程也是在select()的轮询中，当selector事件触发频繁时，accept可能调度不及时，客户端
 *      建立连接就会慢，可以这样改进：
 *          单独用一个线程来阻塞建立连接，将连接维护到一个阻塞队列中；
 *          另起一个线程，死循环从阻塞队列中poll()出连接，完成读写监听注册，并当发生读写时创建任务进行调度；
 *
 * 疑问：
 *      1. select()和selectNow()
 *              一个阻塞，一个非阻塞
 *      2. 循环处理selectionKey集合时，处理完需要从集合中remove
 *              避免已经处理完的事件，仍在集合中，被重复处理
 *      3. 线程池中对同一个socketChannel的两次读事件，多线程安全
 *              需要加锁
 *      4. 同一个socketChannel的一次读操作，有可能触发两次事件吗？
 *              会，只要socketChannel中有数据，那么一直触发select()返回；
 *              假设一次读操作，创建了线程任务之后，线程任务还没被执行，那么下次select()仍会触发
 *              解决：待补充；？？？
 *
 * NIO2：将NIO中的死循环select()改造成事件监听模式
 *
 * NioServerDemo：用NIO来实现网络服务器，代码暂略
 *
 */
public class NioServerDemo {

    class Server {

        public void start() throws IOException {

            //Queue socketQueue = new ArrayBlockingQueue(1024); //move 1024 to ServerConfig

            //this.socketAccepter  = new SocketAccepter(tcpPort, socketQueue);


            //MessageBuffer readBuffer  = new MessageBuffer();
            //MessageBuffer writeBuffer = new MessageBuffer();

            //this.socketProcessor = new SocketProcessor(socketQueue, readBuffer, writeBuffer,  this.messageReaderFactory, this.messageProcessor);

            Queue socketQueue = new ArrayBlockingQueue(1024);
            SocketAccepter socketAccepter = new SocketAccepter(8080, socketQueue);
            Thread accepterThread = new Thread(socketAccepter);

            Thread processorThread = new Thread(new SocketProcessor());

            accepterThread.start();
            processorThread.start();
        }

    }

    /**
     * 阻塞式的获取socket连接，放入queue中
     */
    class SocketAccepter implements Runnable {

        private int tcpPort = 0;
        private ServerSocketChannel serverSocket = null;

        private Queue socketQueue = null;

        public SocketAccepter(int tcpPort, Queue socketQueue) {
            this.tcpPort = tcpPort;
            this.socketQueue = socketQueue;
        }

        public void run() {
            try {
                this.serverSocket = ServerSocketChannel.open();
                this.serverSocket.bind(new InetSocketAddress(tcpPort));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


            while (true) {
                try {
                    SocketChannel socketChannel = this.serverSocket.accept();

                    System.out.println("Socket accepted: " + socketChannel);

                    //todo check if the queue can even accept more sockets.
                    this.socketQueue.add(socketChannel);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * 新建一个Selector
     * 死循环间隔0.1s，
     * 第一步：从socketAcceptor中的阻塞queue中获取socketChannel，设置为非阻塞，
     * 在Selector上注册该channel的read方法；
     * 第二步：调用Selector的select()方法获取发生事件的SelectionKey的集合，遍历集合，
     * 进行处理，处理完成后集合中移除该key，遍历完成后清空集合，这样避免
     * 未发生新的事件，却重复处理之前已经处理的事件；
     */
    class SocketProcessor implements Runnable {

        private Queue<SocketChannel> inboundSocketQueue;

        private Selector readSelector;

        @Override
        public void run() {
            while (true) {
                try {
                    SocketChannel socketChannel = this.inboundSocketQueue.poll();

                    while (socketChannel != null) {
                        socketChannel.configureBlocking(false);

                        SelectionKey key = socketChannel.register(this.readSelector, SelectionKey.OP_READ);
                        //key.attach(newSocket);
                        key.attach(socketChannel);
                        socketChannel = this.inboundSocketQueue.poll();
                    }

                    int readReady = this.readSelector.selectNow();

                    if (readReady > 0) {
                        Set<SelectionKey> selectedKeys = this.readSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();

                            SocketChannel socket = (SocketChannel) key.attachment();
                            //socket.messageReader.read(socket, this.readByteBuffer);

                            //List<Message> fullMessages = socket.messageReader.getMessages();
                            //if (fullMessages.size() > 0) {
                             //   for (Message message : fullMessages) {
                             //       message.socketId = socket.socketId;
                             //       this.messageProcessor.process(message, this.writeProxy);  //the message processor will eventually push outgoing messages into an IMessageWriter for this socket.
                             //   }
                             //   fullMessages.clear();
                           // }

                           // if (socket.endOfStreamReached) {
                            //    System.out.println("Socket closed: " + socket.socketId);
                            //    this.socketMap.remove(socket.socketId);
                            //    key.attach(null);
                            //    key.cancel();
                            //    key.channel().close();
                           // }


                            keyIterator.remove();
                        }
                        selectedKeys.clear();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
