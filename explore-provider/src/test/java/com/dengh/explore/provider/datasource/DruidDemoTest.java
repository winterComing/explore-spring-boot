package com.dengh.explore.provider.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author dengH
 * @title: DruidDemoTest
 * @description: TODO
 * @date 2020/2/5 21:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DruidDemoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) jdbcTemplate.getDataSource();

        System.out.println(dataSource.getUsername());
        System.out.println(dataSource.getMinIdle());
        System.out.println(dataSource.getMaxActive());

        System.out.println(jdbcTemplate.getFetchSize());
        System.out.println(jdbcTemplate.getMaxRows());

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from actor");
        System.out.println(mapList.toString());

    }
}