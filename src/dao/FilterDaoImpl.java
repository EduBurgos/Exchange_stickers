package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilterDaoImpl implements FilterDao {

    private static String FIND_ALL = "SELECT * FROM catalog ";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    public String completeQuery( String query, String nameCard,String category, String classCard, String typeCard) {
        if (!nameCard.equals("") || category != null || !classCard.equals("") || !typeCard.equals("")) {

            if (!nameCard.equals("")) {
                query += " AND CardName=?";
            }
            if (category != null) {
                query += " AND Category=?";
            }
            if (!classCard.equals("")) {
                query += " AND Class=?";
            }
            if (!typeCard.equals("")) {
                query += " AND CardType =?";
            }
        }
        return query;
    }

    public PreparedStatement setQuery( int counter,PreparedStatement p,String name,String category, String classCard, String typeCard) throws SQLException{

        if (!name.equals("")) {
            p.setString(counter, name);
            counter++;
        }
        if (category != null) {
            p.setString(counter, category);
            counter++;
        }
        if (!classCard.equals("")) {
            p.setString(counter, classCard);
            counter++;
        }
        if (!typeCard.equals("")) {
            p.setString(counter, typeCard);
            counter++;
        }
        return p;
    }
}
