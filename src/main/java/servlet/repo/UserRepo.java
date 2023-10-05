package servlet.repo;

import model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface UserRepo<T> {

    void save(T user);

    int login(String email, String password, HttpServletRequest request);
    List<T> findAllUsers() throws SQLException;
    List<T> findAllByAge(int age);
    User findUserByEmail(String email);
    void saveCookieToDatabase(String cookieUUID, long userId);
}
