package com.dengh.explore.provider.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author dengH
 * @title: JdbcDemoTest
 * @description: TODO
 * @date 2020/2/5 19:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcDemoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws SQLException {
        HikariDataSource dataSource = (HikariDataSource) jdbcTemplate.getDataSource();

        System.out.println(dataSource.getUsername());
        System.out.println(dataSource.getMinimumIdle());

        System.out.println(jdbcTemplate.getFetchSize());
        System.out.println(jdbcTemplate.getMaxRows());
    }
}