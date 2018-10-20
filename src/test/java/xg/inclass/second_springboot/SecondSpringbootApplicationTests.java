package xg.inclass.second_springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecondSpringbootApplication.class)
public class SecondSpringbootApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private DataSource dataSource;
//	@Autowired
//	private BookDao bookDao;

	//@Test
//	public void  countBooks(){
//		//int i = bookDao.countBooks();
//		Book bookById = bookDao.getBookById(1L);
//		System.out.println("bookById="+bookById);
//	}

	@Test
	public void contextLoads() {

		System.out.println("dataSource="+
				applicationContext.getBean("dataSource"));
	}

}
