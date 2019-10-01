package servlets;

import collection.CollectionOwn;
import platform.Platform;
import userSide.User;

import javax.servlet.RequestDispatcher;
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
                CollectionOwn logged = confirmPassword(request);
                if(logged!=null)
                {
                    request.getSession().setAttribute("errorMessage", null);
                    request.getSession().setAttribute("logged",logged);
                    response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
                }
                else{

                    request.getSession().setAttribute("errorMessage", "Invalid user or password.Please try again.");
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
        Platform platform = Platform.getInstance();
        return platform.LogIn(name,pass);
    }
}
