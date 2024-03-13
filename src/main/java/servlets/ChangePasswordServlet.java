package servlets;

import java.io.*;

import dto.DatabaseConnector;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            checkStatement.setString(1, username);
            checkStatement.setString(2, oldPassword);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = response.getWriter();
                out.println("{ \"error\": \"Пользователь с текущим именем и паролем не найден\" }");
                return;
            }

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            updateStatement.setString(1, newPassword);
            updateStatement.setString(2, username);
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.println("{ \"message\": \"Пароль пользователя успешно изменен\" }");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                PrintWriter out = response.getWriter();
                out.println("{ \"error\": \"Не удалось изменить пароль пользователя\" }");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}

