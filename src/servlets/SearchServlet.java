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
    private String DEFAULT_ROUTE = "/views/homepage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getSession().setAttribute("filter", null);
            ArrayList<Exchange> filter = searchFilter(request);

            if (filter != null) {
                request.getSession().setAttribute("filter", filter);
                response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);
            } else {
                forwardTo(request, response, INDEXPROFILE_ROUTE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Exchange> searchFilter(HttpServletRequest request) throws SQLException {
        String filterCategory = request.getParameter("filterCategory");
        int category = Integer.parseInt(filterCategory);
        String filterClass = request.getParameter("filterClass");
        String filterType = request.getParameter("filterType");
        String filterCard = request.getParameter("filterCard");
        CollectionOwn logged = (CollectionOwn) request.getSession().getAttribute("logged");
        User user = logged.getOwner();
        Platform platform = Platform.getInstance();
        //serie di if/else per controllare che metodi dao siano giusti
        if (category != 0) {
            return platform.findTByCategoryCard(user, filterCategory);
        } else if (filterClass != null) {
            return platform.findTByClassCard(user, filterClass);
        } else if (filterType != null) {
            return platform.findTByTypeCard(user, filterType);
        } else if (filterCard != null) {
            return platform.findTByNameCard(user, filterCard);
        } else
            return null;

    }
}


