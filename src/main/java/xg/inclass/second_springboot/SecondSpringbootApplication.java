package xg.inclass.second_springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import xg.inclass.second_springboot.properties.OuterApplicationProperties;

@SpringBootApplication(scanBasePackages = "xg.inclass.second_springboot")
//@ComponentScan(basePackages = "xg.inclass.second_springboot")
@MapperScan(basePackages = "xg.inclass.second_springboot.dao")
@EnableConfigurationProperties(OuterApplicationProperties.class)
@EnableAsync
public  class SecondSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecondSpringbootApplication.class, args);
        System.out.println("====================================启动成功=================================================");
        System.out.println("====================================启动成功=================================================");
        System.out.println("====================================启动成功=================================================");

    }
}