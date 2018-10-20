package xg.inclass.second_springboot.async;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;
import xg.inclass.second_springboot.annotation.XgAsync;
import xg.inclass.second_springboot.service.impl.BookServiceImpl;

import java.lang.reflect.Method;

/**
 * @Description:测试联合pointcut的使用 T00052
 * @Author: xuguang
 * @CreateDate: 2018/8/22 21:01
 * @Version: 1.0
 */
@Component
public class XgCombineAsyncPointcut extends StaticMethodMatcherPointcut {


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (method.getName().equals("runAsync")) {
            return true;
        }
        return false;
    }


}