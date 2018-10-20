package xg.inclass.second_springboot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * springboot 外部配置T0005
 * T00003
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "outer")//!由于高版本的去掉了location属性, 所以要加下面的@PropertySource注解来指定位置
@PropertySource("classpath:config/OuterProperty.properties")
public class OuterApplicationProperties {
    private String name;
    private int age;
    private String sex;
}
