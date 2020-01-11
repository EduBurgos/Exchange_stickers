package servlets;

import collection.CollectionOwn;
import dao.FacadeImplements;
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
        if (request.getParameter("delete")!=null)
        {
            String value= request.getParameter("delete");
            int id_trans=Integer.parseInt(value);
            FacadeImplements facadeImplements=new FacadeImplements();
            try {
                facadeImplements.delete(id_trans);
            } catch (Exception e) {
                e.printStackTrace();
            }
            forwardTo(request, response, INDEXPROFILE_ROUTE);
        }

        try{
            CollectionOwn nickname = confirmNickname(request);
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

    /**
     * Method that confirms nickname that will load the own collection.
     * @param request
     * return collection own*/
    private CollectionOwn confirmNickname(HttpServletRequest request) throws SQLException {
        String nickname = request.getParameter("Nickname");
        Platform platform = Platform.getInstance();
        return platform.SnitchCards(nickname);
    }

}
