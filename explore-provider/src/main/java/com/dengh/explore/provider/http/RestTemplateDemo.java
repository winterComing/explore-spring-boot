package com.dengh.explore.provider.http;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author dengH
 * @title: RestTemplateDemo
 * @description: TODO
 * @date 2019/11/25 11:29
 */
public class RestTemplateDemo {

    public static final int maxIdleConnections = 5;

    public static final long keepAliveDuration = 5L;

    public static final long connectionTimeout = 30L;

    public static final long readTimeout = 30L;

    public static void main(String[] args) {
        testURIBuilder();
    }

    public static void testURIBuilder() {
        System.out.println(constructURIBuilder(DefaultUriBuilderFactory.EncodingMode.NONE).expand("http://www.baidu.com/路径?{p}={q}", "姓名", "邓浩"));
        System.out.println(constructURIBuilder(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY).expand("http://www.baidu.com/路径?{p}={q}", "姓名", "邓浩"));
        System.out.println(constructURIBuilder(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT).expand("http://www.baidu.com/路径?{p}={q}", "姓名", "邓浩"));
        System.out.println(constructURIBuilder(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES).expand("http://www.baidu.com/路径?{p}={q}", "姓名", "邓浩"));
    }

    /**
     * url encode
     */
    private static DefaultUriBuilderFactory constructURIBuilder(DefaultUriBuilderFactory.EncodingMode encodingMode) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(encodingMode);
        return uriBuilderFactory;
    }

    /**
     * okHttpClient
     * connectionPool，最多10个空闲连接，5分钟不使用将关闭，连接指的是http连接
     * 设置readTimeout,connectionTimeout
     */
    public static RestTemplate constructRestTemplateOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES));
        builder.connectTimeout(connectionTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS);

        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(builder.build()));
        return restTemplate;
    }

    /**
     * httpClient
     * maxTotal：最大keep-alive保持连接数
     * ValidateAfterInactivity：空闲keep-alive最长时间
     */
    public static RestTemplate constructRestTemplateHttpClient(Registry<ConnectionSocketFactory> registry) {

        PoolingHttpClientConnectionManager connectionManager = null;
        if (registry == null) {
            connectionManager = new PoolingHttpClientConnectionManager();
        } else {
            connectionManager = new PoolingHttpClientConnectionManager(registry);
        }
        connectionManager.setMaxTotal(1000);
        connectionManager.setValidateAfterInactivity(30000);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout((int) connectionTimeout * 1000);
        requestFactory.setReadTimeout((int) readTimeout * 1000);
        requestFactory.setHttpClient(HttpClients.custom().setConnectionManager(connectionManager).build());

        return new RestTemplate(requestFactory);
    }

    /**
     * httpClient 构建有https功能的restTemplate: 绕过证书验证，例如：12306网站
     *
     * @return
     */
    public static RestTemplate constructRestTemplateHttpClientSSLIgnore() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        sslContext.init(null, new TrustManager[]{trustManager}, null);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();

        return constructRestTemplateHttpClient(registry);
    }

    /**
     * httpClient 构建有https功能的restTemplate: 设置信任证书验证，用于自定义证书和密钥的情况
     * keyStorePath 密钥库路径
     * keyStorepass 密钥库密码
     */
    private static final String keyStorePath = "";

    private static final String keyStorepass = "";

    public static RestTemplate constructRestTemplateHttpClientSSLTrust() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = null;
        FileInputStream instream = null;
        KeyStore keyStore = null;
        try {
            // 初始化keyStore
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            keyStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sslContext = SSLContextBuilder.create().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
            }
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext))
                .build();

        return constructRestTemplateHttpClient(registry);
    }

    /**
     * okClient 构建有https功能的restTemplate: 绕过证书验证，例如：12306网站
     *
     * @return
     */
    public static RestTemplate constructRestTemplateOkhttpSSLIgnore() throws NoSuchAlgorithmException, KeyManagementException, IOException, KeyStoreException, CertificateException {
        FileInputStream fileInputStream = new FileInputStream(new File(keyStorePath));
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(fileInputStream, keyStorepass.toCharArray());

        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0])
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES))
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();

        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(client));
        return restTemplate;
    }
}
