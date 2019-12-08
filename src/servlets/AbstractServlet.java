package servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Main servlet to be extended from the others, it contains the method to
 * redirect the servlets and the protected value for the default route It
 * is used to redirect user when he tries a not allowed redirection (i.e. using a doGet method
 * or modifying the URL when it's not allowed to)
 */

public class AbstractServlet extends HttpServlet {
    protected String INDEX_ROUTE = "/index.jsp";
    protected String INDEXPROFILE_ROUTE = "/index_profile.jsp";
    protected String INDEXHOMEPAGE_ROUTE = "/index_homepage.jsp";
    /**
     * Method to redirect the requests to a jsp page. The method handle the
     * communication between pages and navigation flow in a sort of pipe&filter
     * way, using:
     * @param request represents the http request made to the servlet
     * @param response represents the http response
     * @param route represents the route to be redirected
     * @throws ServletException Exception coming from the servlet itself
     * @throws IOException Exception coming from an I/O problem
     */
    protected void forwardTo(HttpServletRequest request, HttpServletResponse response, String route) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher(route);
        rd.forward(request, response);
    }
}
