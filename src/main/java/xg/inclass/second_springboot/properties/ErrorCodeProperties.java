package xg.inclass.second_springboot.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import xg.inclass.second_springboot.test.TestMain;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: T00015 T00038
 * @Author: xuguang
 * @CreateDate: 2018/5/19 23:25
 * @Version: 1.0
 */
@Component
@PropertySource(value = "/config/error_code.properties")
@Data
@Slf4j
public class ErrorCodeProperties //implements InitializingBean
{
    @Autowired
    private Environment environment;

    public ErrorCodeProperties() {
        log.info("PostConstruct的调用顺序,现在是调用构造器,environment="+environment);
    }

    @PostConstruct
    public void init(){
        log.info("PostConstruct的调用顺序,现在是调用PostConstruct注解的方法,environment="+environment);
    }


    /*private Properties prop = new Properties();

    public String getMsgByCode(String code) {
        return prop.getProperty(code);
    }



   //这是基本原理, 上面的@PropertySource注解加environment是方便使用
    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream in = ErrorCodeProperties.class.getClass().
                getResourceAsStream("/config/error_code.properties");
        prop.load(in);
    }*/


}