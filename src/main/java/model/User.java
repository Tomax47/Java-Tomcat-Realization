package model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {

    private long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;


    public boolean userExist() {
        return name != null;
    }
}
