package dao;

import collection.Card;
import userSide.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CollectionOwnDaoImpl implements CollectionOwnDao {
    /**
     * query used to select an exchange in DB
     */
    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE ID_User = (select ID from users where Username = ?)";

    private static final String INSERT_QUERY = "INSERT INTO collections (IDCardColl, ID_Card, ID_User, In_Market)"+"VALUES";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;


    public boolean insert(int id_card_coll, User user, Card card, boolean in_market) {
        conn = null;
        try {
            conn = connector.createConnection();
            System.out.println("query2");
            String query2 = INSERT_QUERY + "("+id_card_coll+", "+user.getUsername()+", "+card.getId()+", "+in_market+")";
            System.out.println("preparestament");
            preparedStatement = conn.prepareStatement(query2);
            System.out.println("execute");
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
    public ArrayList<Card> getCollentionOwn(User user){
        System.out.println("esto iniziando getCollectionOwn");
        ArrayList<Card> c = new ArrayList<Card>();
        //String listaCarte = VIEW_COLLECTION_QUERY;
        user.getUsername();
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(VIEW_COLLECTION_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            System.out.println("Colectionownimp before the while");

            System.out.println("este es result "+result.toString());

            while (result.next() && result != null) {
                System.out.println("imprime "+result.getInt("ID"));
                System.out.println("into while");
                Card card=new Card(result.getInt("ID"),
                        result.getString("Category"),
                        result.getString("Class"),
                        result.getInt("Lvl"),
                        result.getString("Rarity"),
                        result.getString("CardType"),
                        result.getString("CardName"),
                        result.getString("CardDescription"),
                        result.getInt("IDCardColl"));
                System.out.println("prima de add");
                c.add(card);
                System.out.println("dopo add");
                //to do: farla diventare mappa con quantità
                //cards.put(card,result.getInt(9));
                System.out.printf("lista de carte dentro while "+card.getNome());
            }
            return c;
        }catch (SQLException e){
            return null;
        }
    }
}
