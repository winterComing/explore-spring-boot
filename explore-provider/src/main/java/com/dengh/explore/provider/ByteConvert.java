package com.dengh.explore.provider;

import org.apache.tomcat.util.buf.HexUtils;

import java.nio.charset.Charset;

/**
 * @author dengH
 * @title: ByteConvert
 * @description: TODO
 * @date 2019/8/14 9:41
 * 1. 16进制字符串 和 字符编码字符串的 相互转换
 * 可使用工具类HexUtils
 */
public class ByteConvert {

    public static void main(String[] args) {

        System.out.println("61626364".equals(toHexString("abcd")));
        System.out.println("e68891e58699616263313233".equals(toHexString("我写abc123")));

        System.out.println("61626364".equals(HexUtils.toHexString("abcd".getBytes(Charset.forName("utf-8")))));
        System.out.println("e68891e58699616263313233".equals(HexUtils.toHexString("我写abc123".getBytes(Charset.forName("utf-8")))));

        System.out.println("反向写abc123".equals(new String(HexUtils.fromHexString("e58f8de59091e58699616263313233"), Charset.forName("utf-8"))));
        System.out.println("反向写abc123".equals(toStringFromHex("e58f8de59091e58699616263313233")));

    }

    /**
     * 字符串 转 16进制字符串：1. 字符串按utf-8解码得到byte[]；2. 对每一个byte取低8位； 3. 低8位转成16进制 4.拼接即可
     * @param source
     * @return
     */
    public static String toHexString(String source){
        byte[] bytes = source.getBytes(Charset.forName("utf-8"));
        StringBuilder stringBuilder = new StringBuilder();
        for (Byte b: bytes) {
            // &0xff 即取低8位；
            stringBuilder.append(Integer.toHexString(b & 0xff));
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串 转 字符串：1. 两个16进制字符 组成 一个byte；2.byte数组转换成字符串返回
     * @param source
     * @return
     */
    public static String toStringFromHex(String source){
        char[] chars = source.toCharArray();
        byte[] result = new byte[source.length()/2];
        for (int i = 0; i < result.length; i++){
            int high = Integer.parseInt(new String(new char[]{chars[i*2]}), 16) & 0xff;
            int low = Integer.parseInt(new String(new char[]{chars[i*2 + 1]}), 16) & 0xff;
            byte b = (byte) (((high << 4) + low) & 0xff);
            result[i] = b;
        }
        return new String(result, Charset.forName("utf-8"));
    }

}
