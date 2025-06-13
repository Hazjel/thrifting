package controllers;

import classes.JDBC;
import java.io.IOException;
import models.auth.User;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UserControllers", urlPatterns = {"/UserControllers"})
public class UserControllers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                response.sendRedirect("pages/login.jsp?error=empty");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            String hashedPassword = user.getHashedPassword();

            JDBC db = new JDBC();

            try {
                String query = "SELECT u.*, r.id AS role_id, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ? AND u.password = ?";
                ResultSet rs = db.getDataPrepared(query, username, hashedPassword);

                if (rs != null && rs.next()) {
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("role_name")); // set role name ke User (karena User extend Role)
                    user.setId(rs.getInt("role_id")); // set role id ke User

                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    response.sendRedirect("index.jsp");
                } else {
                    response.sendRedirect("pages/login.jsp?error=invalid");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("pages/login.jsp?error=exception");
            }
        }

        else if ("register".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");

            if (username == null || email == null || password == null || confirmPassword == null ||
                    username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                response.sendRedirect("pages/register.jsp?error=empty");
                return;
            }

            if (!password.equals(confirmPassword)) {
                response.sendRedirect("pages/register.jsp?error=nomatch");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            String hashedPassword = user.getHashedPassword();

            JDBC db = new JDBC();

            try {
                String insertQuery = "INSERT INTO users (username, email, password, role_id) VALUES (?, ?, ?, ?)";
                db.runQueryPrepared(insertQuery, user.getUsername(), user.getEmail(), hashedPassword, "2"); // 2 = user
                response.sendRedirect("pages/login.jsp?registered=true");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("pages/register.jsp?error=db");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user login, register, logout using custom JDBC class";
    }
}

