package dao;

import collection.Card;
import userSide.User;

//import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CollectionOwnDaoImpl implements CollectionOwnDao {
    /**
     * query used to select an exchange in DB
     */
    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) Username = ?)";

    private static final String INSERT_QUERY = "INSERT INTO collections (ID_Card, Username, In_Market)"+"VALUES";

    private static final String new_randow_card_for_new_user ="insert into collections values (IDCardColl, (select id from catalog order by rand() limit 1), username = ?,false)";

    private static final String get_last_card_sachet = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE  Username = ? order by IDCardColl desc limit 1";

    private static final String HAS_CARDS_QUERY ="select * from collections where ID_Card = ?, ID_User = ?";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;


    public boolean insert(User user, Card card, boolean in_market) {
        conn = null;
        try {
            conn = connector.createConnection();
            String query2 = INSERT_QUERY + "("+user.getUsername()+", "+card.getId()+", "+in_market+")";
            preparedStatement = conn.prepareStatement(query2);
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
        ArrayList<Card> c = new ArrayList<Card>();
        //String listaCarte = VIEW_COLLECTION_QUERY;
        user.getUsername();
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(VIEW_COLLECTION_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();

            while (result.next() && result != null) {
                Card card=new Card(result.getInt("ID"),
                        result.getString("Category"),
                        result.getString("Class"),
                        result.getInt("Lvl"),
                        result.getString("Rarity"),
                        result.getString("CardType"),
                        result.getString("CardName"),
                        result.getString("CardDescription"));
                c.add(card);
                //to do: farla diventare mappa con quantità
                //cards.put(card,result.getInt(9));
            }
            return c;
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public Card createRandomCard(User user){
        try{
            Card card;
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(new_randow_card_for_new_user);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            card = get_last_card(user);
            return card;
        }catch (SQLException e){
            System.out.println(e.toString());
            return null;
        }

    }

    @Override
    public  Card get_last_card(User user){
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(get_last_card_sachet);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();

            Card card=new Card(result.getInt("ID"),
                    result.getString("Category"),
                    result.getString("Class"),
                    result.getInt("Lvl"),
                    result.getString("Rarity"),
                    result.getString("CardType"),
                    result.getString("CardName"),
                    result.getString("CardDescription"));
            return card;
        }catch (SQLException e){
            return null;
        }
    }

    @Override
    public ArrayList<Card> openSachet(User user){
        ArrayList<Card> c = new ArrayList<Card>();

        for(int i=0; i<6; i++){
            c.add(createRandomCard(user));
        }
        return c;
    }

    //metodo per controllare che possegga le carte inseritegli
    public boolean hasCards(int userID, ArrayList<Card> cards){

        try {
            conn = connector.createConnection();
            //TODO fare in modo che vengo eseguito per ogni carta dell' arraylist, meglio prendere tutte le carte con una query e operare poi con un ciclo
            preparedStatement = conn.prepareStatement(HAS_CARDS_QUERY);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
