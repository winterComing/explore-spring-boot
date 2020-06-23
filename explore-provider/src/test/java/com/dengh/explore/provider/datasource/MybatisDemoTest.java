package com.dengh.explore.provider.datasource;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

/**
 * @author dengH
 * @title: MybatisDemoTest
 * @description: TODO
 * @date 2020/2/5 21:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = {"com.dengh.explore.provider.datasource"})
public class MybatisDemoTest {

    @Autowired
    private ActorMapper actorMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void testSqlSession(){
       // sqlSessionFactory.
    }

    @Test
    public void testQuery() throws SQLException {
        ActorExample example = new ActorExample();
        example.createCriteria().andIdEqualTo(1);
        List<Actor> actors = actorMapper.selectByExample(example);
        System.out.println(JSONObject.toJSONString(actors));
    }
}