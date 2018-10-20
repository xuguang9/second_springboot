package xg.inclass.second_springboot.service;

import org.hibernate.validator.constraints.NotBlank;
import xg.inclass.second_springboot.dto.Book;

public interface BookService {

    Book getBookByIdUseString(@NotBlank(message = "书籍id为空") String  id);

    Book getBookById( Long id);

    void runAsync(Long id);

}
