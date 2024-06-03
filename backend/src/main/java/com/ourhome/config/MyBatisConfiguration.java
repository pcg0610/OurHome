package com.ourhome.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ourhome.**.dao")
public class MyBatisConfiguration {
}
