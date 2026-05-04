package servlets;

import collection.CollectionOwn;
import platform.Platform;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/homepage.jsp";

    /**
     * Checks if username and password from login form are correct.
     * <p>
     *     If all is correct sets some session's parameters for that user.
     *     and redirect the request to a new jsp page (Homepage).
     *     On the other hand if the username or the password are not correct it sets an error message
     *     and redirect the request to the same page (login)
     * </p>
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     *
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
                request.getSession().setAttribute("message", null);
                CollectionOwn logged = confirmPassword(request);
                if(logged!=null)
                {
                    Platform platform=Platform.getInstance();
                    request.getSession().setAttribute("errorMessage", null);
                    request.getSession().setAttribute("logged",logged);
                    request.getSession().setAttribute("exchangesList",platform.getAllExchanges(logged.getOwner()));
                    request.getSession().setAttribute("exchangesToNotify", platform.notifyDoneExchanges(logged.getOwner()));
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
    /**
     *  Handles the HTTP get request, redirecting it to INDEX_ROUTE (index.jsp)
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, INDEX_ROUTE);
    }

    /**
     * Checks if the input fields from the login form are correct using platform's login method
     * which returns the collection of the logged user.
     * @param request HTTP request
     * @return  the collection of the logged user.
     * @throws SQLException Exception caused by database access error
     */
    private CollectionOwn confirmPassword(HttpServletRequest request) throws SQLException {

        String name = request.getParameter("name");
        String pass = request.getParameter("password");
        Platform platform = Platform.getInstance();
        return platform.LogIn(name, pass);
    }
}
