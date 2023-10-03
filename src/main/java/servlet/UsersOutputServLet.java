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
import java.util.List;

@WebServlet("/getUsers")
public class UsersOutputServLet extends HttpServlet {

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
        String action = request.getParameter("action");
        List<User> result = null;

        try {

            result = userRepo.findAllUsers();
            request.setAttribute("usersForJsp", result);
            request.getRequestDispatcher("/jsp/usersView.jsp").forward(request, response);

//            if (action.equals("getUserByEmail")) {
//                String email = request.getParameter("email");
//                User user = (User) userRepo.findUserByEmail();
//
//                response.getWriter().println("Id: "+user.getId()+"\nName: "+user.getName()+"\nSurname: "+user.getSurname()+"\nage: "+user.getAge()+"\nEmail: "+user.getEmail());
//            } else {
//                if (action.equals("getUsersByAge")) {
//
//                    int age = Integer.parseInt(request.getParameter("age"));
//                    result = userRepo.findAllByAge(age);
//
//                } else if (action.equals("getAllUsers")) {
//                    result = userRepo.findAllUsers();
//
//                }
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
