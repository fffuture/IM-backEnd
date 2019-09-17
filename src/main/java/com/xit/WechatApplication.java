package com.xit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@MapperScan(basePackages="com.xit.mapper")
@SpringBootApplication(scanBasePackages={"com.xit.*"},exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages="com.xit.mapper")
public class WechatApplication extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder) {
        return builder.sources(WechatApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(WechatApplication.class, args);
	}
}
 
 