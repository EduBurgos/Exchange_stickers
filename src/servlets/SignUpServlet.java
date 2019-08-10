package servlets;

import dao.UserDaoImpl;
import platform.Platform;
import userSide.User;

import javax.print.DocFlavor;
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

    /**dove imposta name, surname e Username ? **/
    private String name;
    private String surname;
    private String userName;
    private String email;
    private String pass;

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

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String username = request.getParameter("Username");
        String email = request.getParameter("Email");
        String password = request.getParameter("Password");

        User user = new User(name, lastName, username, email);
        UserDaoImpl userTemp = new UserDaoImpl();


        if(userTemp.checkUnique(user)){
            try {
                boolean succ = userTemp.save(user, password);         /**User salvato correttamente*/
                if (succ){
                    forwardTo(request, response, INDEX_ROUTE);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            forwardTo(request, response, INDEX_ROUTE);
        }

        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            forwardTo(request, response, DEFAULT_ROUTE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}