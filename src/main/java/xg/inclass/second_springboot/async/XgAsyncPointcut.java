package xg.inclass.second_springboot.async;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import xg.inclass.second_springboot.annotation.XgAsync;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/8/22 21:01
 * @Version: 1.0
 */
@Component
public class XgAsyncPointcut extends StaticMethodMatcherPointcut {


    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        XgAsync xgAsync = method.getAnnotation(XgAsync.class);
        if (xgAsync != null) {
            return true;
        }
        return false;
    }


}