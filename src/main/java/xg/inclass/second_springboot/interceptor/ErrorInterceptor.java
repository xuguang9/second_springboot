package xg.inclass.second_springboot.interceptor;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xg.inclass.second_springboot.exception.DaoException;
import xg.inclass.second_springboot.properties.ErrorCodeProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Description: T00015
 * 主要是@ControllerAdvice和@ExceptionHandler注解,特别是@ExceptionHandler注解的方法,入参可以取到异常的详细信息
 * @Author: xuguang
 * @CreateDate: 2018/5/19 22:36
 * @Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class ErrorInterceptor {

    @Autowired
    private ErrorCodeProperties errorCodeProperties;

    @ExceptionHandler(DaoException.class) //拦截所有运行时的全局异常
    @ResponseBody //返回 JOSN
    public Exception allExceptionHandler(HttpServletRequest request, Exception exception) throws Exception
    {
        DaoException daoException = (DaoException) exception;
        StackTraceElement[] a = new StackTraceElement[0];
        daoException.setStackTrace(a);
        System.out.println("我报错了："+daoException.getLocalizedMessage());
        System.out.println("我报错了："+daoException.getCause());
        System.out.println("我报错了："+daoException.getSuppressed());
        Environment environment = errorCodeProperties.getEnvironment();
        String msg = environment.getProperty("code." + daoException.getCode());
         daoException.setCode( msg);
        System.out.println("我报错了："+daoException.getStackTrace());
        System.out.println("我报错了的code："+daoException.getCode());
        return daoException;
    }

}