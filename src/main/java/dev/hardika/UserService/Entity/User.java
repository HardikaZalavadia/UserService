package dev.hardika.UserService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "Ecom_User")
public class User extends BaseModel{
    private String name;
    private String emailId;
    private String phoneNumber;
    private String password;
    private String token;
    @ManyToMany
    private List<Role> role;

}
