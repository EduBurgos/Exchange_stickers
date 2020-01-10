package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchServlet", urlPatterns = "/Search")
public class SearchServlet extends AbstractServlet {
    private String DEFAULT_ROUTE = "/views/userprofile.jsp";
    private String HOMEPAGE_ROUTE="/views/homepage.jsp";
    private String EXCHANGE_ROUTE="/views/exchange.jsp";

    /**
     * Checks in which jsp page we are and returns filtered exchanges/cards
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
            String exchanges="http://localhost:8080/progettoF19webApp_war_exploded/views/exchange.jsp";
            if (searchFilter(request)) {
                if(host.equals(profile)) {
                    response.sendRedirect(request.getContextPath() + DEFAULT_ROUTE);
                } else if(host.equals(exchanges)){
                    response.sendRedirect(request.getContextPath()+EXCHANGE_ROUTE);
                } else {
                        response.sendRedirect(request.getContextPath()+HOMEPAGE_ROUTE);
                    }
                }
            else if(host.equals(profile)) {
                forwardTo(request, response, INDEXPROFILE_ROUTE);
            }else if(host.equals(exchanges)){
                forwardTo(request,response,INDEXEXCHANGE_ROUTE );
            } else
                forwardTo(request, response, INDEXHOMEPAGE_ROUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets session's parameters for the filter when at least one of the filters is applied
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

