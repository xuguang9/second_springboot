package xg.inclass.second_springboot.config;

import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import xg.inclass.second_springboot.async.XgAsyncAdvisor;
import xg.inclass.second_springboot.async.XgAsyncInterceptor;
import xg.inclass.second_springboot.async.XgAsyncPointcut;
import xg.inclass.second_springboot.async.XgCombineAsyncPointcut;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/8/22 21:15
 * @Version: 1.0
 */
@Configuration
public class AopConfig {






    /**
     * 异步通知器
     * @param xgAsyncPointcut
     * @param xgCombineAsyncPointcut
     * @param xgAsyncInterceptor
     * @return
     */
    @Bean
    @Role(ROLE_INFRASTRUCTURE)//一定要加上这个role,不然扫描不到
    public XgAsyncAdvisor xgAsyncAdvisor(XgAsyncPointcut xgAsyncPointcut,
                                         XgCombineAsyncPointcut xgCombineAsyncPointcut,
                                         XgAsyncInterceptor xgAsyncInterceptor) {
        ComposablePointcut composablePointcut = new ComposablePointcut();
        //composablePointcut的intersection表示交集,union是并集 T00052
        ComposablePointcut union = composablePointcut.intersection((MethodMatcher) xgAsyncPointcut)
                                                     .intersection((MethodMatcher)xgCombineAsyncPointcut);
//        XgAsyncAdvisor xgAsyncAdvisor = new XgAsyncAdvisor(union,xgAsyncInterceptor);
        XgAsyncAdvisor xgAsyncAdvisor = new XgAsyncAdvisor(xgAsyncPointcut,xgAsyncInterceptor);
        return xgAsyncAdvisor;
    }

}