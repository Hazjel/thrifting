package controllers;

import classes.JDBC;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UserControllers", urlPatterns = {"/UserControllers"})
public class UserControllers extends HttpServlet {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String hashedPassword = hashPassword(password);

            JDBC db = new JDBC();
            ResultSet rs = db.getData("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + hashedPassword + "'");

            try {
                if (rs != null && rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", rs.getString("username"));
                    session.setAttribute("email", rs.getString("email"));
                    response.sendRedirect("index.jsp");
                } else {
                    response.sendRedirect("pages/login.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("login.jsp?error=db");
            }
        }

        // Register juga perlu hash password:
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

            String hashedPassword = hashPassword(password);

            String query = "INSERT INTO users (username, email, password) VALUES ('"
                    + username + "', '" + email + "', '" + hashedPassword + "')";

            JDBC db = new JDBC();
            db.runQuery(query);

            response.sendRedirect("pages/login.jsp?registered=true");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user login, register, logout using custom JDBC class";
    }
}
