package com.dengh.explore.provider.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author dengH
 * @title: AioServerDemo
 * @description: TODO
 * @date 2019/11/22 11:02
 */
public class AioServerDemo {

    public static final String IP = "127.0.0.1";

    public static final Integer PORT = 12701;

    public static void main(String[] args) {
        new Thread(new Server()).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        testClient();
    }

    public static void testClient(){

        System.out.println("client try to connect :" + IP + ",port:" + PORT);
        try {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(IP, PORT);
            socketChannel.connect(socketAddress, null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    socketChannel.write(ByteBuffer.wrap("hello aio".getBytes()));
                    System.out.println("client write: hello aio");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("client connect fail:" + exc.getMessage());
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
class Server implements Runnable {
    public static final String IP = "127.0.0.1";

    public static final Integer PORT = 12701;

    @Override
    public void run() {
        System.out.println("server listen on " + IP + ",port:" +  PORT);
        SocketAddress socketAddress = new InetSocketAddress(IP, PORT);
        AsynchronousServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open().bind(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.clear();
                try {
                    result.read(byteBuffer).get(100, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
                System.out.println("server收到：" + new String(byteBuffer.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("server accept fail." + exc.getMessage());
            }
        });
        int i = 0;
        do{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            System.out.println("doing other things");
        }while (i <= 15);
    }
}












