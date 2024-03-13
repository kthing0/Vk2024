package servlets;

import dto.DatabaseConnector;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import user.User;
import user.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = UserDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            String token = generateToken();
            try {
                saveToken(username, token, DatabaseConnector.getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.getSession().setAttribute("user", user);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println("{ \"token\": \"" + token + "\" }");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            out.println("{ \"error\": \"Неверное имя пользователя или пароль\" }");
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void saveToken(String username, String token, Connection connection) throws SQLException, SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("UPDATE users SET token = ? WHERE username = ?");
        insertStatement.setString(1, token);
        insertStatement.setString(2, username);
        insertStatement.executeUpdate();
    }
}





