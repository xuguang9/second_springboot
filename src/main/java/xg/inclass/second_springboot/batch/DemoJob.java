package xg.inclass.second_springboot.batch;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xg.inclass.second_springboot.quartz.JobInfoDto;

import javax.sql.DataSource;

/**
 * @Description:T00041
 * @Author: xuguang
 * @CreateDate: 2018/5/30 21:43
 * @Version: 1.0
 */
@Configuration
@Import({BatchConfig.class})
public class DemoJob {
    @Bean
    public ItemReader itemReader(SqlSessionFactory sqlSessionFactory){
        MyBatisCursorItemReader itemReader = new MyBatisCursorItemReader();
        itemReader.setSqlSessionFactory(sqlSessionFactory);
        itemReader.setQueryId("xg.inclass.second_springboot.dao.BookDao.getAllJobs");
        return itemReader;
    }

    @Bean
    public ItemProcessor itemProcessor() {
        return obj->{
            System.out.println("开始批量执行="+obj);
            return obj;
        };
    }

   /* @Bean
    public ItemWriter itemWriter(SqlSessionFactory sqlSessionFactory) {
        MyBatisBatchItemWriter itemWriter = new MyBatisBatchItemWriter();
        itemWriter.setSqlSessionFactory(sqlSessionFactory);
        itemWriter.setStatementId("xg.inclass.second_springboot.dao.BookDao.getAllJobs");
        return itemWriter;
    }*/

  /* @Bean
    public Step demoStep2(StepBuilderFactory stepBuilderFactory,
                         ItemReader itemReader,
                         ItemWriter itemWrite){
        return stepBuilderFactory.get("demoStep2")
                .chunk(1)
                .reader(itemReader)
                .writer(itemWrite).build();
    }*/


   @Bean
    public Job demoJob2(JobBuilderFactory factory,
           ItemReader itemReader,ItemProcessor itemProcessor,
           StepBuilderFactory stepBuilderFactory
         ){
        return factory.get("demoJob2")
                .incrementer(new RunIdIncrementer())
                .flow(stepBuilderFactory.get("demoStep")
                        .chunk(2)
                        .reader(itemReader)
                        .processor(itemProcessor).build())
                .end().build();
    }

}