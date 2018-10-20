package xg.inclass.second_springboot.aop;

import org.apache.ibatis.annotations.Arg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.websocket.ClientEndpoint;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/10/15 23:09
 * @Version: 1.0
 */
@Component
@Aspect
public class ValidAspect {

    @Pointcut("@annotation(xg.inclass.second_springboot.annotation.ValidParam)")
    public void validPointcut() {
    }

    @Before("validPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("拦截了");
        MethodInvocationProceedingJoinPoint methodJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        Object[] args1 = methodJoinPoint.getArgs();
        Object aThis = methodJoinPoint.getThis();
        Object[] args = joinPoint.getArgs();
    }

}