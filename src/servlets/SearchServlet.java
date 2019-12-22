package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SearchServlet", urlPatterns = "/Search")
public class SearchServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/userprofile.jsp";
    private String HOMEPAGE_ROUTE="/views/homepage.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    /**
     * Method that checks in which jsp page we are and return filtered exchanges/cards
     * <p>
     *     If we are in jsp page (userprofile) when we use the search filter navbar
     *     it sends the request to the same page and it shows filtered cards
     *     If we are in jsp page(homepage) or (exchange) when we use the search filter navbar
     *     it sends the request to the jsp page(homepage) and it shows cards filtered exchanges
     *
     * </p>
     * @param request HTTP request
     * @param response HTTP response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that sets session's parameters for the filter when at least
     * one of the filters is applied
     * @param request HTTP request
     * @return true if at least one of the filters is applied, false if no filter is used
     */
    private boolean searchFilter(HttpServletRequest request) {
        String filterCategory = request.getParameter("filterCategory");
        request.getSession().setAttribute("category", filterCategory);
        String filterClass = request.getParameter("filterClass");
        request.getSession().setAttribute("class", filterClass);
        String filterType = request.getParameter("filterType");
        request.getSession().setAttribute("type", filterType);
        String filterCard = request.getParameter("filterCard");
        request.getSession().setAttribute("card", filterCard);
        if (filterCategory!=null ||!filterClass.equals("")||!filterCard.equals("")||!filterType.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}

