package xg.inclass.second_springboot.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;
import xg.inclass.second_springboot.dto.Book;
import xg.inclass.second_springboot.dto.ValueListDto;
import xg.inclass.second_springboot.quartz.JobInfoDto;

import java.util.List;

public interface BookDao {

    @Cacheable(cacheNames = "books",key = "#p0")
    Book getBookById(@Param(value = "id") Long id);

    int countBooks();

    List<ValueListDto> getAllValues();

    ValueListDto getValueByKey(String  key);

    List<JobInfoDto> getAllJobs();

    int updateBookById(@Param(value = "id")Long id);

    Book getBook(@Param(value = "title") String  title);

}
