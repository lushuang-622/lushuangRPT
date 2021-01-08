package com.ssd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
* @author lus E-mail:***@163.com
* @date 2020年4月14日 下午2:25:44
* @Description: TODO 类说明
* @version V1.0
* @since JDK 1.8
*/
/**
 * 配置分页插件
 *
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
