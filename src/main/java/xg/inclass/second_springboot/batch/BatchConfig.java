package xg.inclass.second_springboot.batch;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Description:T00041
 * @Author: xuguang
 * @CreateDate: 2018/5/30 20:57
 * @Version: 1.0
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {


    /**
     * job处理仓库
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository jobRepository(@Qualifier("data1")DataSource dataSource, PlatformTransactionManager transactionManager)
            throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.name());
        jobRepositoryFactoryBean.setTablePrefix("batch_");
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * job执行器
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public SimpleJobLauncher jobLauncher(@Qualifier("data1")DataSource dataSource, PlatformTransactionManager transactionManager)
            throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    /**
     * job配置
     */
    @Bean
    BatchConfigurer batchConfigurer(@Qualifier("data1")DataSource dataSource,
                                    PlatformTransactionManager transactionManager){
        return new BatchConfigurer() {
            @Override
            public JobRepository getJobRepository() throws Exception {
                return jobRepository(dataSource,transactionManager);
            }

            @Override
            public PlatformTransactionManager getTransactionManager() throws Exception {
                return transactionManager;
            }

            @Override
            public JobLauncher getJobLauncher() throws Exception {
                return jobLauncher(dataSource,transactionManager);
            }

            @Override
            public JobExplorer getJobExplorer() throws Exception {
                JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
                jobExplorerFactoryBean.setDataSource(dataSource);
                jobExplorerFactoryBean.afterPropertiesSet();
                return jobExplorerFactoryBean.getObject();
            }
        };
    }


}