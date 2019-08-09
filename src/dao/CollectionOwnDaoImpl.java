package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.User;

import java.sql.*;
import java.util.*;

public class CollectionOwnDaoImpl implements CollectionOwnDao {
    /**
     * query used to select an exchange in DB
     */
    private static final String VIEW_COLLECTION_QUERY = "SELECT catalog.*, collections.Quantity FROM collections, catalog WHERE collections.Username=? And catalog.ID=collections.IDCard";
    private static final String INSERT_QUERY = "INSERT INTO collections (Username, IDCard, Quantity)"+"VALUES";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    @Override
    public boolean insert(Card card,User user,int quantity) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            String query = INSERT_QUERY + "('"+user.getUsername()+"', '"+card.getId()+"', '"+quantity+"')";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return false;

    }

    @Override
    public boolean update() throws SQLException {
        return false;
    }

    @Override
    public boolean delete() throws SQLException {
        return false;
    }

    @Override
    public synchronized TreeMap<Card,Integer> create_collection (User user) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(VIEW_COLLECTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            TreeMap<Card,Integer> cards=new TreeMap<>();
            while (result.next() && result != null) {
               Card card=new Card(result.getInt(1),result.getString(2),result.getString(3),result.getInt(4),result.getString(5),result.getString(6),result.getString(7),result.getString(8));
               //to do: farla diventare mappa con quantità
               cards.put(card,result.getInt(9));
            }
             return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert result != null;
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return null;
    }
}


