package servlets;

import java.io.*;

import dto.DatabaseConnector;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String username = request.getParameter("username");

        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            deleteStatement.setString(1, username);
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out = response.getWriter();
                out.println("{ \"message\": \"Пользователь успешно удален\" }");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = response.getWriter();
                out.println("{ \"error\": \"Пользователь не найден\" }");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
}
