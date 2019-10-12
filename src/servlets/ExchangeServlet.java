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

@WebServlet(name = "servlets.HomePageServlet", urlPatterns = "/exchange")
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
            for (int i=0; i<cardsToTake.length;i++){
                System.out.println(cardsToTake[i]);
            }
            /*for(int i=0; i<cardsToTake.length; i++){
                System.out.println(cardsToTake[i]);
            }*/
            String cardsToGive[] = request.getParameterValues("cardsToGive");
            System.out.println(Arrays.toString(cardsToTake));
            System.out.println(Arrays.toString(cardsToGive));

            int[] intArrayToGive = new int[cardsToGive.length];
            int[] intArrayToTake = new int[cardsToTake.length];
            //passo da array di stringhe ad array di interi
            for (int i=0; i<cardsToGive.length;i++){
                intArrayToGive[i] = Integer.parseInt(cardsToGive[i]);
            }
            for (int i=0; i<cardsToTake.length;i++){
                intArrayToTake[i] = Integer.parseInt(cardsToTake[i]);
            }

            CollectionOwn collectionOwn=(CollectionOwn) request.getSession().getAttribute("logged");

            String username=collectionOwn.getOwner().getUsername();
            Platform platform = Platform.getInstance();
            //TODO come valorizziamo idtrans

            //TODO lavorare con i vettori per attuare lo scambio
            platform.setExchange(username, intArrayToGive, intArrayToTake);
            //settaggio del parametro che farà capire alla jsp che deve uscire il pop up del riuscito settaggio dello scambio
            response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
        } catch (Exception e) {
            e.printStackTrace();
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
