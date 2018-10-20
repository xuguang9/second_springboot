package xg.inclass.second_springboot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import xg.inclass.second_springboot.annotation.XgAsync;
import xg.inclass.second_springboot.dao.BookDao;
import xg.inclass.second_springboot.dto.Book;
import xg.inclass.second_springboot.service.BookService;

import javax.validation.constraints.NotNull;


@Service("bookService")
@Slf4j
@Validated
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;


    @Override
    public Book getBookByIdUseString(@NotBlank(message = "书籍id为空") String id) {
        log.info("调用dao层前=========");
        Book b = bookDao.getBookById(Long.valueOf(id));
        log.info("调用dao层后=========");
        return b;
    }

    @Override
    public Book getBookById( Long id) {
        log.info("调用dao层前=========");
        Book b = bookDao.getBookById(id);
        log.info("调用dao层后=========");
        return b;
    }

//    @Async
    @Override
    @XgAsync
    public void runAsync(Long id) {
        System.out.println("===现在执行service方法");
    }
}
