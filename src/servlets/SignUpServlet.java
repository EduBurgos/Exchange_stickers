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

@WebServlet(name = "SignUpServlet", urlPatterns = "/signUp")
public class SignUpServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/homepage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        try {
                User saved=saveUser(request);
                if(saved!=null)
                {
                    request.getSession().setAttribute("logged",saved);
                    forwardTo(request, response, DEFAULT_ROUTE);
                }
            else{
                forwardTo(request, response, INDEX_ROUTE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            forwardTo(request, response, INDEX_ROUTE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User saveUser(HttpServletRequest request) throws SQLException {
        String name = request.getParameter("name");
        String surname= request.getParameter("surname");
        String mail= request.getParameter("mail");
        String username= request.getParameter("username");
        Platform platform= Platform.getInstance();
        return platform.signUp(name,surname,mail,username);
    }


}