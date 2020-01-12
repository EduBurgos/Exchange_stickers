package servlets;
/*StandardCharsets.US_ASCII*/
import platform.Platform;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SignUpServlet", urlPatterns = "/signUp")
public class SignUpServlet extends AbstractServlet {

    private String DEFAULT_ROUTE = "/views/homepage.jsp";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String secretkey = "chiavesupersegretissimaXD";

        String name = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String username = request.getParameter("Username");
        String email = request.getParameter("Email");
        String password = Platform.encrypt(request.getParameter("Password"), secretkey);
        String retype = request.getParameter("ReType");
        request.getSession().setAttribute("message", null);
        Platform platform = Platform.getInstance();
        try{
            if (platform.signUp(name, lastName, username, email, password, retype)) {
                request.getSession().setAttribute("message", "You have successfully signed up!");

                /**CREO LE 6 CARTE ALLA REGISTRAZIONE*/
                platform.userStartUpCards(name, lastName, username, email);

                forwardTo(request, response, INDEX_ROUTE);
            }
            else{
                request.getSession().setAttribute("message", "User already signed up");
                forwardTo(request, response, INDEX_ROUTE);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        try {
            forwardTo(request, response, DEFAULT_ROUTE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}