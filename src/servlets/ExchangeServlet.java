package servlets;

import platform.Platform;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.HomePageServlet", urlPatterns = "/exchange")
public class ExchangeServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "../homepage.jsp";

    /**
     * Method to handle the HTTP post request, redirecting it to the defined route (DEFAULT_ROUTE)
     * @param request represents the HTTP request
     * @param response represents the HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */

    // TODO: improve
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //prendere gli id delle carte da scambiare
        String username = request.getParameter("username");
        //TODO request.getParameter per le carte da dare e da ricevere
        Platform platform= Platform.getInstance();
        //platform.setExchange();
        //settaggio del parametro che farà capire alla jsp che deve uscire il pop up del riuscito settaggio dello scambio
        forwardTo(request, response, DEFAULT_ROUTE);
    }

    /**
     * Method to handle the HTTP get request; in this case is possible to reach welcome.jsp either from doGet or doPost
     * @param request is the HTTP request
     * @param response is the HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O error
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, DEFAULT_ROUTE);
    }
}