package servlet;

import servlet.implementation.UserRepoImplementation;
import servlet.repo.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@WebServlet("/setting")
public class SettingsPage extends HttpServlet {


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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("color")) {
                    String color = cookie.getValue();
                    request.setAttribute("selectedColor", color);
                }
                if (cookie.getName().equals("cookie_uuid")) {
                    String uuid = cookie.getValue();
                    if (userRepo.checkUserByCookieUUID(uuid)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("loggedIn", true);
                    }
                }
            }
        }
        request.getRequestDispatcher("/jsp/settings.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String color = request.getParameter("color");
        System.out.println(color);

        //Cookie to save into the db
        String cookieUUID = UUID.randomUUID().toString();
        userRepo.saveCookieToDatabase(cookieUUID, getUserIdFromSession(request, response));

        //We generate an uuid and then add it in the Cookie (first one is for the name "whatever the name is", the second is for the uuid)
        Cookie colorCookie = new Cookie("color", color);
        response.addCookie(colorCookie);
        //max age -1 deletes the cookie after closing the browser, 0 delete it right away!
        // the parameter is taken in seconds
        colorCookie.setMaxAge(240);
        response.sendRedirect("/setting");
    }

    private long getUserIdFromSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("loggedIn") != null) {
            return getLoggedInUserId(session);
        } else {
            response.getWriter().println("User ain't logged in!");
            throw new IllegalStateException("User is not logged in.");
        }
    }

    private long getLoggedInUserId(HttpSession session) {
        Object userIdObject = session.getAttribute("userId");

        if (userIdObject != null) {
            try {
                return Long.parseLong(userIdObject.toString());
            } catch (NumberFormatException e) {
                throw new IllegalStateException("User ID in session is not a valid number.");
            }
        } else {
            throw new IllegalStateException("User ID is not found in session.");
        }
    }


}
