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
    final String secretkey = "chiavesupersegretissimaXD";

    /**
     * method to check if username and password from login form are correct.
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
                CollectionOwn logged = confirmPassword(request);
                if(logged!=null)
                {
                    Platform platform=Platform.getInstance();
                    request.getSession().setAttribute("errorMessage", null);
                    request.getSession().setAttribute("logged",logged);
                    request.getSession().setAttribute("exchangesList",platform.getAllExchanges(logged.getOwner()));
                    request.getSession().setAttribute("exchangesToNotify", platform.notifyDoneExchanges(logged.getOwner()));
                    platform.giftCard(logged.getOwner());
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

    /**
     * Checks if the input fields from the login form are correct using platform's login method
     * which returns the collection of the logged user.
     * @param request HTTP request
     * @return CollectionOwn the card's collection of the logged user.
     * @throws SQLException Exception coming from database
     */
    private CollectionOwn confirmPassword(HttpServletRequest request) throws SQLException {

        String name = request.getParameter("name");
        String pass = Platform.encrypt(request.getParameter("password"), secretkey);
        //String pass= request.getParameter("password");
        Platform platform = Platform.getInstance();
        return platform.LogIn(name,pass);
    }
}
