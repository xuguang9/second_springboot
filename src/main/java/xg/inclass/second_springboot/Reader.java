package xg.inclass.second_springboot;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Reader   {
    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String fullname;
    private String password;


}
