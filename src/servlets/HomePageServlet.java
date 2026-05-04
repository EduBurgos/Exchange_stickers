package servlets;

import collection.CollectionOwn;
import platform.Platform;
import servlets.AbstractServlet;
import userSide.Exchange;
import userSide.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that handles requests between web pages(catalogue.jsp, confirmPage.jsp, navbar.jsp, reservationState.jsp).
 */

@WebServlet(name = "HomePageServlet", urlPatterns = "/homepage")
public class HomePageServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/homepage.jsp";



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
                CollectionOwn logged = (CollectionOwn) request.getSession().getAttribute("logged");
                User u = logged.getOwner();
                request.getSession().setAttribute("doneExchange", "true");
                request.getSession().setAttribute("exchangesList",platform.getAllExchanges(u));
                CollectionOwn updatedCollection = platform.getMyCollection(u.getUsername());
                if (updatedCollection != null) {
                    request.getSession().setAttribute("logged", updatedCollection);
                }
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
        String username=((CollectionOwn)request.getSession().getAttribute("logged")).getOwner().getUsername();

        System.out.println(idExchange);
        ArrayList<Exchange> exchangesList = (ArrayList<Exchange>)request.getSession().getAttribute("exchangesList");
        Exchange targetExchange = null;
        for (Exchange e : exchangesList) {
            if (e.getId_trans() == idExchange) {
                targetExchange = e;
                break;
            }
        }
        if (targetExchange == null) return false;
        return platform.marketExchange(targetExchange, username);
    }

}
