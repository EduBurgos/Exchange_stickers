package servlets;

import collection.Card;
import dao.CardsDao;
import dao.CardsDaoImpl;
import platform.Platform;

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
    private String DEFAULT_ROUTE = "/views/search.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardTo(request, response, INDEX_ROUTE);
    }



}


