package com.ssd;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author lus E-mail:***@163.com
 * @date 2020年12月23日 下午1:50:05
 * @Description:
 * @version V1.0
 * @since JDK 1.8
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan(basePackages={"com.ssd"})
@MapperScan("com.ssd.monitor.dao")
public class App {
    public static void main(String[] args) {
        //String 应用启动起来
        SpringApplication.run(App.class, args);
    }
}
