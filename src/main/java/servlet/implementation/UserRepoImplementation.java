package servlet.implementation;

import model.User;
import servlet.repo.UserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepoImplementation implements UserRepo<User> {

    private Connection connection;
    private Statement statement;
    private String SAVE_USER = "INSERT INTO users_table (name, surname, age, email, password) VALUES ";
    private String FIND_ALL_USERS = "SELECT * FROM users_table";
    private String FIND_ALL_BY_AGE = "SELECT * FROM users_table WHERE age=";
    private String FIND_USER_BY_EMAIL = "SELECT * FROM users_table WHERE email=";
    private String SAVE_COOKIE = "INSERT INTO users_cookies (user_id, cookie_uuid) VALUES ";

    public UserRepoImplementation(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }

    public void save(User user) {

        String PSQL_USER_REGISTER = SAVE_USER + "('"+ user.getName()+"','"+user.getSurname()+"',"+user.getAge()+",'"+user.getEmail()+"','"+user.getPassword()+"')";

        try {
            statement.executeUpdate(PSQL_USER_REGISTER);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        System.out.println(user.getEmail()+", with passwd => "+user.getPassword()+" has been registered!");
    }

    public int login(String email, String password, HttpServletRequest request) {

        User user = findUserByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {

                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                return 1;
            } else {
                System.out.println("Incorrect user credentials!");
            }
        } else {
            System.out.println("User ain't been found!");
        }
        return 0;
    }

    public List<User> findAllUsers() {

        try {
            List<User> users_in_database = new ArrayList<>();

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .age(resultSet.getInt("age"))
                        .surname(resultSet.getString("surname"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();

                users_in_database.add(user);
            }

            if (users_in_database.isEmpty()) {
                System.out.println("Log: Gte-All request for an empty database table!");
            }
            return users_in_database;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<User> findAllByAge(int age) {

        try {
            List<User> users_in_database = new ArrayList<>();

            String FIND_USERS_BY_AGE = FIND_ALL_BY_AGE+age;

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_USERS_BY_AGE);

            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();

                users_in_database.add(user);
            }

            if (users_in_database.isEmpty()) {
                System.out.println("Log: Get-All-By-Age request for an empty database table!");
            }
            return users_in_database;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User findUserByEmail(String email) {

        User user = null;
        try {

            String USER_BY_EMAIL = FIND_USER_BY_EMAIL+"'"+email+"'";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(USER_BY_EMAIL);

            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .age(resultSet.getInt("age"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
            }

            if (user == null) {
                System.out.println("No user has been found by the email "+email);
            }
            System.out.println("The user "+email+" has been found!");
            return user;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void saveCookieToDatabase(String cookieUUID, long userId) {
        String PSQL_COOKIE_REGISTER = SAVE_COOKIE + "(" + userId + ", '" + cookieUUID + "')";
        try {
            statement.executeUpdate(PSQL_COOKIE_REGISTER);
            System.out.println("Cookie UUID has been saved in the database!");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean checkUserByCookieUUID(String cookieUUID) {
        String CHECK_USER_BY_COOKIE_UUID = "SELECT * FROM users_cookies WHERE cookie_uuid='" + cookieUUID + "'";

        try {
            ResultSet resultSet = statement.executeQuery(CHECK_USER_BY_COOKIE_UUID);

            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
