package servlets;

import collection.CollectionOwn;
import platform.Platform;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "ProfileServlet", urlPatterns = "/userprofile")
public class ProfileServlet extends AbstractServlet{

    private String DEFAULT_ROUTE = "/views/userprofile.jsp";



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        forwardTo(request, response, INDEX_ROUTE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            CollectionOwn nickname = confirmNickname(request);
            //ArrayList<Exchange> lista_market = Platform.getInstance().getExchange();
            request.getSession().setAttribute("snitch",null);

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

    private CollectionOwn confirmNickname(HttpServletRequest request) throws SQLException {
        String nickname = request.getParameter("Nickname");
        Platform platform = Platform.getInstance();
        return platform.SnitchCards(nickname);
    }

}
