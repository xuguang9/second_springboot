package xg.inclass.second_springboot.application;

import org.springframework.boot.builder.SpringApplicationBuilder;
import xg.inclass.second_springboot.SecondSpringbootApplication;

public class ReadingListServletInitializer  {
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SecondSpringbootApplication.class);
    }


}
