package com.dengh.explore.provider.distribute;

import java.util.UUID;

/**
 * @author dengH
 * @title: UuidDemo
 * @description: TODO
 * @date 2019/11/12 9:33
 *
 * uuid是32位，每位16进制数字，共有5个版本，几乎不重复
 * java实现UUID.randomUUID()是用伪随机数，用UUID.nameUUIDFromBytes(byte[])每次生成都是一致的；
 * https://www.jianshu.com/p/da6dae36c290
*/
public class UuidDemo {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));

        System.out.println("-------------------------");
        System.out.println(UUID.nameUUIDFromBytes("ABC".getBytes()).toString().replace("-", ""));
        System.out.println(UUID.nameUUIDFromBytes("ABC".getBytes()).toString().replace("-", ""));
    }


}
