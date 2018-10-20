package xg.inclass.second_springboot.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: T00014
 * @Author: xuguang
 * @CreateDate: 2018/5/20 13:45
 * @Version: 1.0
 */
@Component //为了启动更快,注释掉
public class JobFactory extends AdaptableJobFactory{

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        //进行注入,这一步解决不能spring注入bean的问题
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}