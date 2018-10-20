package xg.inclass.second_springboot.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import xg.inclass.second_springboot.interceptor.LoginInterceptor;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 10:01
 * @Version: 1.0
 */
@Component
@Slf4j
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/login/*");
        super.addInterceptors(registry);
    }
}