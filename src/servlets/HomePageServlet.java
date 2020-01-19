package servlets;

import collection.CollectionOwn;
import platform.Platform;
import servlets.AbstractServlet;
import userSide.Exchange;
import userSide.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that handles requests between web pages(catalogue.jsp, confirmPage.jsp, navbar.jsp, reservationState.jsp).
 */

@WebServlet(name = "HomePageServlet", urlPatterns = "/homepage")
public class HomePageServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/homepage.jsp";
    private String PROFILE_ROUTE = "/views/userprofile.jsp";


    /**
     * Handles the HTTP post request, redirecting it to the defined route (DEFAULT_ROUTE)
     * <p>
     *     displays a message if the exchange has been successfully completed or not
     * </p>
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Platform platform = Platform.getInstance();
        try {
            boolean resultExchange = marketExchange(request);
            if (resultExchange) {
                User u=(User)request.getSession().getAttribute("user");
                request.getSession().setAttribute("doneExchange", "true");
                request.getSession().setAttribute("exchangesList",platform.getAllExchanges(u));
                request.getSession().setAttribute("logged",platform.getMyCollection(u.getUsername()));
                request.getSession().setAttribute("role", "notification");
                request.getSession().setAttribute("exchangesToNotify", platform.notifyDoneExchanges(u));
                response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);
            } else {
                request.getSession().setAttribute("doneExchange", "false");
                //forwardTo(request, response, DEFAULT_ROUTE);
                response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Allows to accept an exchange
     * @param request HTTP request
     * @return true if the exchange is successfully accepted false otherwise
     * @throws SQLException exception caused by database access error
     *
     * */
    private boolean marketExchange(HttpServletRequest request) throws SQLException {

        String id=request.getParameter("btn");
        int idExchange =Integer.parseInt(id);
        Platform platform = Platform.getInstance();
        //provare a passare direttamente oggetto utile
        String username=((CollectionOwn)request.getSession().getAttribute("logged")).getOwner().getUsername();
        return platform.marketExchange(((ArrayList<Exchange>)request.getSession().getAttribute("exchangesList")).get(idExchange),username);
    }

}
