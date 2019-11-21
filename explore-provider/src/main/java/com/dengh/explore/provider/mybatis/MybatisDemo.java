package com.dengh.explore.provider.mybatis;

import com.dengh.explore.provider.http.HttpDemo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author dengH
 * @title: MybatisDemo
 * @description: TODO
 * @date 2019/9/30 10:45
 */
public class MybatisDemo {

    public static void main(String[] args) {
        SqlSessionFactory build = null;
        try {
            build = new SqlSessionFactoryBuilder().build(new FileInputStream(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SqlSession sqlSession = build.openSession();
        HttpDemo mapper = sqlSession.getMapper(HttpDemo.class);


    }
}
