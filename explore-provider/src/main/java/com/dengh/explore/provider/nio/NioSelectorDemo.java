package com.dengh.explore.provider.nio;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author dengH
 * @title: NioSelectorDemo
 * @description:
 * @date 2019/7/22 15:57
 *
 *  nio selector的demo：
 * 1. accept不调用，就会一直触发accept事件
 * 2. socketChannel的read()没有读取完数据，就会一直触发read事件
 * 3. client断开连接之后，会一直触发read事件，并返回-1，此时需要取消注册和关闭channel
 *
 */
public class NioSelectorDemo {

    public static void main(String[] args) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testSelect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    testSocket();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void testSelect() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9090));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            // int i = selector.select();
            int i = selector.selectNow();
            if (i<=0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                System.out.println(JSONObject.toJSONString(next));
                if (next.isAcceptable()){
                    System.out.println("有新人进来了，accpet事件发生");
                    /**  通过调用accept()之后，accept事件就不会重复触发 **/
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                }
                if (next.isReadable()){
                    System.out.println("有socketChannel数据可读，read事件发生");
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                    /** 如果不读取，就会一直触发读事件 */
                    int read = socketChannel.read(byteBuffer);
                    System.out.println("读取字节数：" + read);
                    /** 在client断开连接后，会一直触发读事件，并且读到字节数为-1 */
                    if (read == -1){
                        next.cancel();
                    }
                }
                iterator.remove();
            }
        }
    }

    public static void testSocket() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9090));
        ByteBuffer byteBuffer = ByteBuffer.allocate(10068);
        byteBuffer.put(Charset.forName("utf-8").encode("很高兴认识大家"));
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        Thread.sleep(2000);
        byteBuffer.clear();
        byteBuffer.put(Charset.forName("utf-8").encode("大家好"));
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        socketChannel.close();
    }

}
