package xg.inclass.second_springboot.async;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/8/22 21:13
 * @Version: 1.0
 */
public class XgAsyncAdvisor  extends AbstractPointcutAdvisor {

    private Pointcut pointcut;
    private Advice advice;

    public XgAsyncAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

}