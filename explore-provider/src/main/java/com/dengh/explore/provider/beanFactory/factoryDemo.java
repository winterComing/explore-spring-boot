package com.dengh.explore.provider.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author dengH
 * @title: factoryDemo
 * @description: beanFactory创建的全过程
 * @date 2019/7/30 10:10
 *
 * 1. AnnotationConfigApplicationContext
 *      空构造()：初始化一些成员变量，如beanFactory
 * 		register(annotatedClasses)：将@Configuration修饰的配置类放入beanDefinitionMap中
 * 	        1.1 register()之后：
 *  * 	            definitionMap  6(有配置类)  singletonObjs 0  beanPostProcessors 0；
 * 		refresh()：
 * 	        1.2 prepareBeanFactory()之后：标准初始化容器beanFactory
 *                  definitionMap  6(有配置类)  singletonObjs 3  beanPostProcessors 2；
 * 	        1.3 invokeBeanFactoryPostProcessors(): 实例化并调用所有BeanFactoryPostProcessors的方法
 *                  definitionMap  10(有配置类，和配置类@Bean修饰的)  singletonObjs 9（有配置类和bfpp）  beanPostProcessors 2；
 * 	        1.4 registerBeanPostProcessors()：实例化BeanPostProcessors
 *                  definitionMap  10  singletonObjs 12(添加了配置中的bpp)  beanPostProcessors 7(添加了配置中的bpp)；
 * 	        1.5 finishBeanFactoryInitialization()：完成剩余bean的实例化
 *                  definitionMap  10  singletonObjs 16(添加了配置中的普通bean)  beanPostProcessors 7；
 *     说明：beanPostProcessor在每个bean的实例化前后均会调用；
 *     注意：并没有开启注解扫描，normalbean不会被spring管理；
 *
 *  2. getBean(XXX)：完成懒加载bean的实例化
 *
 *  3. 注解扫描：通过@ComponentScan这个注解，在内部的BeanFactoryPostProcessor来执行注解扫描的逻辑，加载bean定义到beanDefinitonMap中；
 *
 *  4. 执行顺序：
 *  execute firstBeanFacotryPostProcessor 's postProcessBeanFactory
 *  construct second Bean：user
 *  execute beanNameAware
 *  execute firstBeanPostProcessor 's postProcessBeforeInitialization
 *  execute InitializingBean 's afterPropertiesSet()
 *  secondBean init method
 *  execute firstBeanPostProcessor 's postProcessAfterInitialization
 *  execute second Bean: preDestory
 *  execute DisposableBean 's destroy
 *  secondBean destory method
 */
@Configuration
@ComponentScan
public class factoryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(factoryDemo.class);
        applicationContext.getBean("firstBean");
        applicationContext.getBean("normalBean");
        applicationContext.close();
    }

    @Bean
    @Lazy
    public String firstBean(){
        System.out.println("construct first Bean");
        return "firstBean";
    }

    @Bean(initMethod = "secondBeanInitMethod", destroyMethod = "secondBeanDestroyMethod")
    public User secondBean(){
        return new User();
    }

    @Bean
    public BeanPostProcessor firstBeanPostProcessor(){
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("execute firstBeanPostProcessor 's postProcessBeforeInitialization");
                return null;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                System.out.println("execute firstBeanPostProcessor 's postProcessAfterInitialization");
                return null;
            }
        };
    }

    @Bean
    public BeanFactoryPostProcessor firstBeanFactoryPostProcessor(){
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                System.out.println("execute firstBeanFacotryPostProcessor 's postProcessBeanFactory");
            }
        };
    }

    class User implements BeanNameAware, InitializingBean, DisposableBean {

        public User() {
            System.out.println("construct second Bean：user");
        }

        @PostConstruct
        private void postConstruct(){
            System.out.println("execute second Bean: postConstruct");
        }

        @PreDestroy
        private void preDestroy(){
            System.out.println("execute second Bean: preDestory");
        }

        private void secondBeanInitMethod(){
            System.out.println("secondBean init method");
        }

        private void secondBeanDestroyMethod(){
            System.out.println("secondBean destory method");
        }


        @Override
        public void setBeanName(String name) {
            System.out.println("execute beanNameAware");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("execute InitializingBean 's afterPropertiesSet()");
        }

        @Override
        public void destroy() throws Exception {
            System.out.println("execute DisposableBean 's destroy");
        }
    }
}
