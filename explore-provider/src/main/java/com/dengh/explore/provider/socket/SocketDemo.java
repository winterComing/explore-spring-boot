package com.dengh.explore.provider.socket;

import java.net.Socket;
import java.net.SocketException;

/**
 * @author dengH
 * @title: SocketDemo
 * @description: socket示例
 * @date 2019/7/25 15:00
 */
public class SocketDemo {

    public static void main(String[] args) {

        printSocketDefaultValue();
    }

    private static void printSocketDefaultValue(){
        Socket socket = new Socket();
        try {
            System.out.println("keepAlive:" + socket.getKeepAlive());
            System.out.println("sendBufferSize:" + socket.getSendBufferSize());
            System.out.println("receiveBufferSize:" + socket.getReceiveBufferSize());
            System.out.println("tcpNoDelay:" + socket.getTcpNoDelay());
            System.out.println("soTimeout:" + socket.getSoTimeout());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}
