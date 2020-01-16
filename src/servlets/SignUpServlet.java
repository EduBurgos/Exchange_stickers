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

     /**
     *Handles the HTTP post request, redirecting it to the defined route (INDEX_ROUTE, which is login.jsp).
     * <p>
     *     allows to get information about the user who would like to sign up.
     *     In case of successful registration the user is registered and receives six cards otherwise error message is displayed
     * </p>
     *  @param request  HTTP request
     *  @param response  HTTP response
     *  @throws ServletException Exception coming from the servlet itself
     *  @throws IOException Exception coming from an I/O problem
     * */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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





}