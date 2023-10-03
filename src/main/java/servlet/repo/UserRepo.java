package servlet.repo;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepo<T> {

    void save(T user);

    int login(String email, String password);
    List<T> findAllUsers() throws SQLException;
    List<T> findAllByAge(int age);
    User findUserByEmail(String email);

}
