package xg.inclass.second_springboot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.writer.CompositeMetricWriter;
import org.springframework.boot.actuate.metrics.writer.DefaultCounterService;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import xg.inclass.second_springboot.filter.SessionFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class MyConfig {


    /**
     * spring的bean校验器
     * @return
     */
    @Bean
    public BeanPostProcessor beanValidationPostProcessor() {
        return new BeanValidationPostProcessor();
    }

   /*
    @Bean //注入一个bean,被上面的BeanPostProcessor校验到了 T00050
    public BookRequsetParam bookRequsetParam() {
        BookRequsetParam bookRequsetParam =new BookRequsetParam();
//        bookRequsetParam.setTitle("有值");
        return bookRequsetParam;
    }
    */

    /**
     * 自定义数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @Qualifier("data1")
    public DataSource dataSource() throws SQLException {
        DataSource d = DataSourceBuilder.create().build();
        return d;
    }

    /**
     * 自定义事物管理
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("data1") DataSource dataSource) {
        return  new DataSourceTransactionManager(dataSource);
    }

    /**
     * 自定义mybatis sqlfactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("data1")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }


    /**
     * spring线程管理T00019
     * @return
     */
   /* @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);//核心线程数
        executor.setMaxPoolSize(16);//最大线程数
        executor.setKeepAliveSeconds(3000);//线程最大空闲时间
        executor.setQueueCapacity(200);//队列大小
        executor.setThreadNamePrefix("XG_TASK_EXECUTOR");//
        //用户可以选择使用自定义策略，只需实现RejectedExecutionHandler接口即可
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }*/


    /**
     * 多任务时的Scheduler，动态设置Trigger。一个SchedulerFactoryBean可能会有多个Trigger
     */
    @Bean(name = "multitaskScheduler")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    /**
     * 计数器bean
     */
    @Bean
    @Qualifier
    public CounterService defaultCounterService() {
        MetricWriter statsdMetricWriter = new CompositeMetricWriter();
        CounterService counterService = new DefaultCounterService(statsdMetricWriter);
        return counterService;
    }

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter());
        registration.addUrlPatterns("/filter/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
//        registration.setOrder();//多个过滤器可以排序
        return registration;
    }

    /**
     * 创建一个过滤器bean
     *
     * @return
     */
    @Bean(name = "sessionFilter")
    public Filter sessionFilter() {
        return new SessionFilter();
    }


    /*@Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }
        };
    }

    *//**
     * T00013 自定义内嵌的tomcat
     * 效果相当于修改tomcat文件下的conf/context.xml的<Resource>标签的值</Resource>
     * @return
     *//*
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = (TomcatEmbeddedServletContainerFactory) container;
                    tomcatEmbeddedServletContainerFactory.addContextCustomizers(new TomcatContextCustomizer() {
                        @Override
                        public void customize(Context context) {
                            ContextResource mydatasource = new ContextResource();
                            mydatasource.setName("jdbc/mydatasource");
                            mydatasource.setAuth("Container");
                            mydatasource.setType("javax.sql.DataSource");
                            mydatasource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                            mydatasource.setScope("Sharable");
                            mydatasource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                            mydatasource.setProperty("url", "jdbc:mysql://localhost:3307/boot");
                            mydatasource.setProperty("username", "root");
                            mydatasource.setProperty("password", "root");
                            context.getNamingResources().addResource(mydatasource);

                        }
                    });
                }
            }
        };
    }*/
}
