package servlets;

import collection.CollectionOwn;
import dao.CollectionOwnDaoImpl;
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



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try{
            CollectionOwn nickname = confirmNickname(request);
            if(nickname != null){
                request.getSession().setAttribute("Nickname",nickname);
                response.sendRedirect(request.getContextPath()+DEFAULT_ROUTE);
            }
            else{
                forwardTo(request, response, INDEX_ROUTE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, INDEX_ROUTE);

    }

    private CollectionOwn confirmNickname(HttpServletRequest request) throws SQLException {
        String nickname = request.getParameter("Nickname");
        Platform platform = Platform.getInstance();
        return platform.SnitchCards(nickname);
    }


}
