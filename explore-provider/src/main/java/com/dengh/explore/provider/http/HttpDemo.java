package com.dengh.explore.provider.http;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author dengH
 * @title: HttpDemo
 * @description: http示例
 * @date 2019/7/25 17:52
 */
public class HttpDemo {

    public static void main(String[] args) {

    }

    public static void printHttpDefaultValue(){
        try {
            URL url = new URL("http://www.baidu.com");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            System.out.println("connectTimeout:" + httpURLConnection.getConnectTimeout());
            System.out.println("readTimeout:" + httpURLConnection.getReadTimeout());
            System.out.println("" );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
