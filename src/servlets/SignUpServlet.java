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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{

        return;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        forwardTo(request, response, INDEX_ROUTE);
    }


}