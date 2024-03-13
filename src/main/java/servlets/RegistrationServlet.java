package servlets;

import java.io.*;

import dto.DatabaseConnector;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;
import org.json.*;

@WebServlet("/registration-servlet")

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("message", "Пользователь уже существует");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.executeUpdate();

            response.setStatus(HttpServletResponse.SC_CREATED);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Пользователь успешно зарегистрирован");
            response.getWriter().write(jsonResponse.toString());
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}
