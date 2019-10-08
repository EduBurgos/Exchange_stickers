package servlets;
/*StandardCharsets.US_ASCII*/
import dao.CollectionOwnDao;
import dao.CollectionOwnDaoImpl;
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
import java.math.BigInteger;
import java.security.interfaces.RSAKey;
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

        final String secretkey = "Khaledmerda";



        String name = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String username = request.getParameter("Username");
        String email = request.getParameter("Email");
        String password = Platform.encrypt(request.getParameter("Password"), secretkey);
        //String password = request.getParameter("Password");
        String retype = request.getParameter("ReType");
        request.getSession().setAttribute("message", null);
        Platform platform = Platform.getInstance();
        try{
            if (platform.SignUp(name, lastName, username, email, password, retype)) {
                request.getSession().setAttribute("message", "You have successfully signed up!");
                /**CREO LE 6 CARTE ALLA REGISTRAZIONE*/
                //passare da platform
                User u = new User(name, lastName, username, email);
                CollectionOwnDao cart = new CollectionOwnDaoImpl();
                for(int i = 0; i<6; i++) {
                    cart.createRandomCard(u);
                }
                forwardTo(request, response, INDEX_ROUTE);

            }
            else{
                request.getSession().setAttribute("message", "User already signed up");
                forwardTo(request, response, INDEX_ROUTE);
            }
        } catch(SQLException e){
            e.printStackTrace();
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