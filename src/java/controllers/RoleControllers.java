package controllers;

import classes.JDBC;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import models.auth.Role;

@WebServlet(name = "RoleControllers", urlPatterns = {"/RoleControllers"})
public class RoleControllers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JDBC db = new JDBC();
        List<Role> roles = new ArrayList<>();

        try {
            String query = "SELECT * FROM roles";
            ResultSet rs = db.getData(query);

            while (rs != null && rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }

            request.setAttribute("roles", roles);
            request.getRequestDispatcher("pages/roles.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles fetching of user roles";
    }
}