package xg.inclass.second_springboot.dto;

import lombok.Data;
import xg.inclass.second_springboot.common.BookStatus;

import java.io.Serializable;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 10:19
 * @Version: 1.0
 */
@Data
public class Book implements Serializable{
    private Long id;
    private String  author;
    private String description;
    private String  bookNum;
    private String title;
    private BookStatus bookStatus;
    private String reader;

    public Book() {
    }

    public Book(Long id, String author) {
        this.id = id;
        this.author = author;
    }

    public Book(Long id, String author, String reader) {
        this.id = id;
        this.author = author;
        this.reader = reader;
    }
}