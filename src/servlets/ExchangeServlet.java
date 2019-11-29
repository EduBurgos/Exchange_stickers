package servlets;

import collection.Card;
import collection.CollectionOwn;
import dao.CardsDaoImpl;
import platform.Platform;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "servlets.ExchangeServlet", urlPatterns = "/exchange")
public class ExchangeServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/homepage.jsp";

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
        try {
            String[] cardsToTake = request.getParameterValues("cardsToTake");
            String cardsToGive[] = request.getParameterValues("cardsToGive");

            System.out.println("carte da prendere "+ Arrays.toString(cardsToTake));
            System.out.println("carte da dare "+ Arrays.toString(cardsToGive));
            //TODO cambiare controllo sul null, non solo se carte da scambiare sono uguali
            if(cardsToGive ==null) {
                cardsToGive = cardsToTake.clone();
            }
            ArrayList<Integer> intArrayToGive = new ArrayList<>();
            ArrayList<Integer> intArrayToTake = new ArrayList<>();
            //passo da array di stringhe ad array di interi
            for (int i=0; i<cardsToGive.length;i++){
                    intArrayToGive.add(Integer.parseInt(cardsToGive[i]));
            }
            for (int i=0; i<cardsToTake.length;i++){
                    intArrayToTake.add(Integer.parseInt(cardsToTake[i]));
            }

            CollectionOwn collectionOwn=(CollectionOwn) request.getSession().getAttribute("logged");

            String username=collectionOwn.getOwner().getUsername();
            Platform platform = Platform.getInstance();
            platform.setExchange(username, intArrayToGive, intArrayToTake);
            //settaggio del parametro che farà capire alla jsp che deve uscire il pop up del riuscito settaggio dello scambio

            response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/views/exchange.jsp");
        }
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
