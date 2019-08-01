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

    private String name;
    private String surname;
    private String userName;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{

        return;
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            forwardTo(request, response, INDEX_ROUTE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}