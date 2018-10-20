package xg.inclass.second_springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import xg.inclass.second_springboot.service.BookService;
import xg.inclass.second_springboot.service.impl.BookServiceImpl;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/21 21:57
 * @Version: 1.0
 */
@Configuration
public class RmiConfig {

    @Autowired
    @Qualifier("bookService")
    private BookService bookService;

    /**
     * 服务端,发布服务
     *
     * @return
     */
    @Bean
    public RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("BookService");
        rmiServiceExporter.setService(bookService);
        rmiServiceExporter.setServiceInterface(BookService.class);
        rmiServiceExporter.setRegistryPort(8080);
        return rmiServiceExporter;
    }

    /**
     * 客户端,调用服务
     */
    /*@Bean("rmiService")
    public RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://192.168.1.104:8081/BookService");
        rmiProxyFactoryBean.setServiceInterface(BookService.class);
        return rmiProxyFactoryBean;
    }*/


}