package com.dengh.explore.provider.jpa;

import com.alibaba.fastjson.JSONObject;
import com.dengh.explore.provider.constant.DbConstants;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dengH
 * @title: JpaOperationDemo
 * @description: TODO
 * @date 2019/9/18 11:45
 */
public class JpaOperationDemo {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setName("dengh");
        user.setAge("28");
        userDao.add(user);

        userDao.delete();
    }

    private static JdbcTemplate jdbcTemplateSingle;

    static{
        /*DataSource dataSource = new MysqlDataSource();
        ((MysqlDataSource) dataSource).setURL(DbConstants.URL);
        ((MysqlDataSource) dataSource).setUser(DbConstants.USER_NAME);
        ((MysqlDataSource) dataSource).setPassword(DbConstants.PASSWORD);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplateSingle = jdbcTemplate;*/
    }

    static class UserDao extends BaseDao<User>{
        @Override
        void add(User bean) {
            super.add(bean);
        }
    }

    static class BaseDao<T>{

        void delete() {
            System.out.println(this.getClass());
            System.out.println(this.getClass().getGenericSuperclass());
            System.out.println((Class) ((ParameterizedType) this.getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[0]);
        }
        void add(T bean){
            MyTable annotation = bean.getClass().getAnnotation(MyTable.class);
            System.out.println(annotation.value());
            String sql = "insert into " +  annotation.value() + " values " + "(";
            for (Field field : bean.getClass().getDeclaredFields()){
                sql += " ?, ";
            }
            sql = sql.substring(0, sql.length()-2);
            sql += ")";
            System.out.println(sql);

            List<Object> paramList = new ArrayList<>();
            for (Field field : bean.getClass().getDeclaredFields()){
                try {
                    field.setAccessible(true);
                    paramList.add(field.get(bean));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(JSONObject.toJSONString(paramList));

            jdbcTemplateSingle.update(sql, paramList.toArray());
        }
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyTable{
        String value();
    }

    @MyTable(value = "t_user")
    static
    class User {

        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }



}
