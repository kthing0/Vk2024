package servlets;

import java.io.*;

import dto.DatabaseConnector;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/rename-user")
public class RenameUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String oldUsername = request.getParameter("oldUsername");
        String newUsername = request.getParameter("newUsername");

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStatement.setString(1, oldUsername);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = response.getWriter();
                out.println("{ \"error\": \"Пользователь с текущим именем не найден\" }");
                return;
            }

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            updateStatement.setString(1, newUsername);
            updateStatement.setString(2, oldUsername);
            int rowsUpdated = updateStatement.executeUpdate();

            if (rowsUpdated > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.println("{ \"message\": \"Имя пользователя успешно изменено\" }");
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                PrintWriter out = response.getWriter();
                out.println("{ \"error\": \"Не удалось изменить имя пользователя\" }");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}

