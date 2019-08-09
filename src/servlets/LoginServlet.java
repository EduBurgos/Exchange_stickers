package servlets;

import collection.CollectionOwn;
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
    private String DEFAULT_ROUTE = "/views/homepage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
                CollectionOwn logged=confirmPassword(request);
                if(logged!=null)
                {
                    System.out.println(logged);
                    request.getSession().setAttribute("logged",logged);
                    response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
                }
                else{
                    forwardTo(request, response, INDEX_ROUTE);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, INDEX_ROUTE);
    }

    private CollectionOwn confirmPassword(HttpServletRequest request) throws SQLException {
        String name = request.getParameter("name");
        String pass= request.getParameter("password");
        System.out.println(pass);
        Platform platform= Platform.getInstance();
        return platform.LogIn(name,pass);
    }
}
