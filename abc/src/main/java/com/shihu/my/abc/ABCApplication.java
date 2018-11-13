package com.shihu.my.abc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
@MapperScan("com.shihu.my.abc.dao")
public class ABCApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ABCApplication.class, args);
	}
}
