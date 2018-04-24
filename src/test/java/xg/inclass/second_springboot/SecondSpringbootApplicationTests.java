package xg.inclass.second_springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondSpringbootApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;


	@Test
	public void contextLoads() {
		System.out.println("helloController="+applicationContext.getBean("helloController"));
	}

}
