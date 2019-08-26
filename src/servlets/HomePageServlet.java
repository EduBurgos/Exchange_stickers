package servlets;

import collection.CollectionOwn;
import platform.Platform;
import servlets.AbstractServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * Class that handles requests between web pages(catalogue.jsp, confirmPage.jsp, navbar.jsp, reservationState.jsp).
 */

@WebServlet(name = "servlets.HomePageServlet", urlPatterns = "/homepage")
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
        forwardTo(request, response, DEFAULT_ROUTE);
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
        System.out.println(toSearch);
        request.getAttribute("logged");
        // TODO: AGGIUNGERE ECCEZIONE CARTA NON TROVATA
        Platform platform= Platform.getInstance();
        platform.searcIntoCollection((CollectionOwn)request.getSession().getAttribute("logged"),toSearch);
        response.sendRedirect(request.getContextPath()+PROFILE_ROUTE);
    }
}
