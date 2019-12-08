package servlets;

import collection.Card;
import collection.CollectionOwn;
import dao.CardsDao;
import dao.CardsDaoImpl;
import platform.Platform;
import userSide.Exchange;
import userSide.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "SearchServlet", urlPatterns = "/Search")
public class SearchServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/userprofile.jsp";
    private String HOMEPAGE_ROUTE="/views/homepage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String host=request.getHeader("Referer");
            String profile="http://localhost:8080/progettoF19webApp_war_exploded/views/userprofile.jsp";
            if (searchFilter(request)) {
                if(host.equals(profile)) {
                    response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);
                }
                    else {
                        response.sendRedirect(request.getContextPath()+HOMEPAGE_ROUTE);
                    }
                }
             else if(host.equals(profile)) {
                forwardTo(request, response, INDEXPROFILE_ROUTE);
            }
            else
                forwardTo(request, response, INDEXHOMEPAGE_ROUTE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean searchFilter(HttpServletRequest request) throws SQLException {
        String filterCategory = request.getParameter("filterCategory");
        request.getSession().setAttribute("category", filterCategory);
        String filterClass = request.getParameter("filterClass");
        request.getSession().setAttribute("class", filterClass);
        String filterType = request.getParameter("filterType");
        request.getSession().setAttribute("type", filterType);
        String filterCard = request.getParameter("filterCard");
        request.getSession().setAttribute("card", filterCard);
        // metodi per il filtro applicato
        if (!filterCategory.equals("0")) {
            return true;
        } else if (!filterClass.equals("")) {
            return true;
        } else if (filterCard != null) {
            return true;
        } else if (!filterType.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}

