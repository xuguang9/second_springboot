package xg.inclass.second_springboot.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import xg.inclass.second_springboot.quartz.JobFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description: 本类的作用是创建Schedule给ScheduleTriggerService使用,T00014
 * @Author: xuguang
 * @CreateDate: 2018/5/20 13:47
 * @Version: 1.0
 */
@Configuration //为了启动快,注释掉
public class QuartzConfig {

    //此处的job工厂是项目自定义的job工厂
    @Autowired(required = false)
    private JobFactory jobFactory;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //加载上面的属性bean
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        //加载自定义的工厂
        schedulerFactoryBean.setJobFactory(jobFactory);

        return schedulerFactoryBean;
    }



    // 创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
}