//package com.dengh.explore.provider.transaction;
//
//import oracle.jdbc.pool.OracleDataSource;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//
//import java.sql.SQLException;
//
///**
// * @author dengH
// * @title: TransactionDemo
// * @description: spring的transaction示例
// * @date 2019/7/31 17:24
// */
//public class TransactionDemo {
//
//    public static void main(String[] args) {
//        beginOneTransaction();
//    }
//
//    public static void beginOneTransaction(){
//        PlatformTransactionManager transactionManager = new DataSourceTransactionManager();
//        OracleDataSource dataSource = null;
//        try {
//            dataSource = new OracleDataSource();
//            dataSource.setURL("jdbc:mysql://192.168.9.100/docker_db?useUnicode=true&characterEncoding=UTF-8");
//            dataSource.setUser("root");
//            dataSource.setPassword("1804052");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        ((DataSourceTransactionManager) transactionManager).setDataSource(dataSource);
//        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        // 开启事务
//        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
//        try{
//
//            // 开启嵌套事务
//            TransactionDefinition transactionDefinitionNest = new DefaultTransactionDefinition();
//            ((DefaultTransactionDefinition) transactionDefinitionNest).setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//            TransactionStatus transactionStatusNest = transactionManager.getTransaction(transactionDefinitionNest);
//            try{
//
//                transactionManager.commit(transactionStatusNest);
//                throw new Exception();
//            }catch (Exception e){
//                transactionManager.rollback(transactionStatusNest);
//            }
//
//            transactionManager.commit(transactionStatus);
//        }catch (Exception e){
//            transactionManager.rollback(transactionStatus);
//        }
//
//    }
//}
