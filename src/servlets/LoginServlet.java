package servlets;

import platform.Platform;
import userSide.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/login.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            User logged=confirmPassword(request);
            if(logged!=null)
            {
                request.getSession().setAttribute("logged",logged);
                forwardTo(request, response, DEFAULT_ROUTE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        forwardTo(request, response, INDEX_ROUTE);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, INDEX_ROUTE);
    }



    private User confirmPassword(HttpServletRequest request) throws SQLException {
        String name = request.getParameter("name");
        String pass= request.getParameter("password");
        Platform platform= Platform.getInstance();
        return platform.LogIn(name,pass);
    }
}
