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
     * Method to handle the HTTP post request, redirecting it to the defined route (DEFAULT_ROUTE)
     * @param request represents the HTTP request
     * @param response represents the HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */

    // TODO: improve
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            boolean resultExchange = marketExchange(request);
            if (resultExchange) {
                request.getSession().setAttribute("doneExchange", "true");
                response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);
            } else {
                request.getSession().setAttribute("doneExchange", "false");
                forwardTo(request, response, DEFAULT_ROUTE);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to handle the HTTP get request; in this case is possible to reach welcome.jsp either from doGet or doPost
     * method
     * @param request is the HTTP request
     * @param response is the HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O error
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //richiesta
        String toSearch = request.getParameter("search");
        Platform platform= Platform.getInstance();
        CollectionOwn collectionOwn=(CollectionOwn) request.getSession().getAttribute("logged");
        if (toSearch==null)
        {
            try {
                collectionOwn = platform.LogIn(collectionOwn.getOwner().getUsername(), collectionOwn.getOwner().getPass());
            }
            catch (SQLException e)
            {

            }
        }
        else
        {
            collectionOwn=platform.searcIntoCollection((CollectionOwn)request.getSession().getAttribute("logged"),toSearch); //mettere in un thread perchè se clicco altrove si impalla
        }
        request.getSession().removeAttribute("logged");
        request.getSession().setAttribute("logged",collectionOwn);
        response.sendRedirect(request.getContextPath()+PROFILE_ROUTE);
        // TODO: AGGIUNGERE ECCEZIONE CARTA NON TROVATA


    }

    private boolean marketExchange(HttpServletRequest request) throws SQLException {

        String id=request.getParameter("btn");
        int idExchange =Integer.parseInt(id);
        Platform platform = Platform.getInstance();
        //provare a passare direttamente oggetto utile
        String username=((CollectionOwn)request.getSession().getAttribute("logged")).getOwner().getUsername();
        ArrayList<Exchange> c=(ArrayList<Exchange>)request.getSession().getAttribute("exchangesList");
        return platform.marketExchange(((ArrayList<Exchange>)request.getSession().getAttribute("exchangesList")).get(idExchange),username);
    }

}
