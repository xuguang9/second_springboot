package xg.inclass.second_springboot.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xg.inclass.second_springboot.dao.BookDao;
import xg.inclass.second_springboot.dto.ValueListDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: T00016
 * @Author: xuguang
 * @CreateDate: 2018/5/19 21:31
 * @Version: 1.0
 */
@Component
public class ValueList implements InitializingBean {

    private Map<String,String> store = new HashMap<>();

    @Autowired
    private BookDao bookDao;

    @Override
    public void afterPropertiesSet() throws Exception {
       /* List<ValueListDto> values = bookDao.getAllValues();
        store = values.stream().collect(
                Collectors.toMap((ValueListDto b) -> b.getKey(), b -> b.getValue()));*/
    }

    public String  getStoreByKey(String key) {
        return store.get(key);
    }


}