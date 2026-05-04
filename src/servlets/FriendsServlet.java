package servlets;

import collection.CollectionOwn;
import dao.FriendshipDaoImpl;
import dao.UserDaoImpl;
import userSide.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FriendsServlet", urlPatterns = "/friends")
public class FriendsServlet extends AbstractServlet {

    private static final String FRIENDS_PAGE = "/views/friends.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CollectionOwn logged = (CollectionOwn) request.getSession().getAttribute("logged");
        if (logged == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        loadFriendsData(request, logged.getOwner().getUsername());
        forwardTo(request, response, FRIENDS_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CollectionOwn logged = (CollectionOwn) request.getSession().getAttribute("logged");
        if (logged == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        String username = logged.getOwner().getUsername();
        String action = request.getParameter("action");

        FriendshipDaoImpl dao = new FriendshipDaoImpl();

        if ("search".equals(action)) {
            String query = request.getParameter("query");
            if (query != null && !query.trim().isEmpty()) {
                try {
                    UserDaoImpl userDao = new UserDaoImpl();
                    List<User> results = userDao.searchByUsername(query.trim(), username);
                    request.setAttribute("searchResults", results);
                    request.setAttribute("searchQuery", query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("send".equals(action)) {
            String receiver = request.getParameter("receiver");
            if (receiver != null && !receiver.equals(username)) {
                if (!dao.requestExists(username, receiver)) {
                    dao.sendRequest(username, receiver);
                    request.setAttribute("message", "Richiesta inviata a " + receiver + "!");
                } else {
                    request.setAttribute("message", "Richiesta già inviata o siete già amici.");
                }
            }
        } else if ("accept".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.acceptRequest(id, username);
        } else if ("decline".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.declineRequest(id, username);
        }

        loadFriendsData(request, username);
        forwardTo(request, response, FRIENDS_PAGE);
    }

    private void loadFriendsData(HttpServletRequest request, String username) {
        FriendshipDaoImpl dao = new FriendshipDaoImpl();
        request.setAttribute("friends", dao.getFriends(username));
        request.setAttribute("pendingRequests", dao.getPendingRequests(username));
    }
}
