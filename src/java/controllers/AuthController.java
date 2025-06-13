package controllers;

import dao.UserDAO;
import models.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);
        switch (action) {
            case "login":
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                break;
            case "register":
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);
        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email dan password harus diisi!");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            return;
        }
        User user = userDao.login(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRoleType());
            if (user.getRoleType().equals("seller")) {
                response.sendRedirect(request.getContextPath() + "/pages/dashboard-admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/dashboard.jsp");
            }
        } else {
            request.setAttribute("error", "Email atau password salah!");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (username == null || email == null || password == null || confirmPassword == null ||
                username.trim().isEmpty() || email.trim().isEmpty() ||
                password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "Semua field harus diisi!");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password dan Confirm Password harus sama!");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }
        try {
            userDao.register(username, email, password);
            User user = userDao.login(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRoleType());
                response.sendRedirect(request.getContextPath() + "/pages/user/dashboard.jsp");
            } else {
                request.setAttribute("error", "Registrasi berhasil, tapi login gagal. Silakan login manual.");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Registrasi gagal! " + e.getMessage());
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/auth/login");
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "login";
        }
        return pathInfo.substring(1);
    }
}
