package servlet;

import model.User;
import servlet.implementation.UserRepoImplementation;
import servlet.repo.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/users")
public class UsersServLet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";

    private UserRepo userRepo;

    @Override
    public void init() throws ServletException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            this.userRepo = new UserRepoImplementation(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request,response);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if (action.equals("register")) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            int age = Integer.parseInt(request.getParameter("age"));
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .age(age)
                    .email(email)
                    .password(password)
                    .build();

            userRepo.save(user);

            response.getWriter().println("User has been added!");

        } else if (action.equals("login")) {

        }
    }
}
