package servlets;

import collection.CollectionOwn;
import dao.FacadeImplements;
import platform.Platform;
import userSide.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "ProfileServlet", urlPatterns = "/userprofile")
public class ProfileServlet extends AbstractServlet{

    private String DEFAULT_ROUTE = "/views/userprofile.jsp";

    /**
     * Handle the HTTP post request, redirecting it to INDEX_ROUTE (index.jsp)
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        forwardTo(request, response, INDEX_ROUTE);
    }

    /**
     * Handles the HTTP get request, redirecting it to defined route
     * <p>
     *     Allows the logged user to delete the exchanges created and to see other users' profile
     * </p>
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Platform platform= Platform.getInstance();
        if (request.getParameter("delete")!=null)
        {
            String value= request.getParameter("delete");
            int id_trans=Integer.parseInt(value);

            try {
                platform.delete(id_trans);
            } catch (Exception e) {
                e.printStackTrace();
            }
            forwardTo(request, response, INDEXPROFILE_ROUTE);
        }

        try{
            CollectionOwn nickname = confirmNickname(request);
            request.getSession().setAttribute("snitch",null);

            //parte della notifica di quando qualcuno accetta una tua trattativa e da fare il controllo per poi aggiornare la tua collezione aggiornata
            request.getSession().setAttribute("role", "notification");
            request.getSession().setAttribute("exchangesToNotify", platform.notifyDoneExchanges((User)request.getSession().getAttribute("user")));

            if(nickname != null){
                request.getSession().setAttribute("snitch",nickname);
                response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
            }
            else{
                forwardTo(request, response, INDEXPROFILE_ROUTE);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ServletException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Confirms nickname that will load the own collection.
     * @param request http request
     * @return collection that belongs to the user searched
     * @throws SQLException exception caused by database access error
     * */
    private CollectionOwn confirmNickname(HttpServletRequest request) throws SQLException {
        String nickname = request.getParameter("Nickname");
        Platform platform = Platform.getInstance();
        return platform.SnitchCards(nickname);
    }

}
