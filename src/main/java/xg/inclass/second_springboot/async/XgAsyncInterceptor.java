package xg.inclass.second_springboot.async;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/8/22 21:07
 * @Version: 1.0
 */
@Component
public class XgAsyncInterceptor implements MethodInterceptor {

    ExecutorService executors = Executors.newSingleThreadExecutor();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Callable callable = ()->{
            try {
                return invocation.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        };
        Future future = executors.submit(callable);
        return future;
    }


}