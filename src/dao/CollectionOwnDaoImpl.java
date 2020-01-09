package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.User;

//import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CollectionOwnDaoImpl implements CollectionOwnDao {

    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) AND Username = ?";

    private static final String INSERT_QUERY = "INSERT INTO collections (ID_Card, Username)"+"VALUES";

    private static final String new_randow_card_for_new_user ="insert into collections(ID_Card,USERNAME) values ((select id from catalog order by rand() limit 1),?)";

    private static final String get_last_card_sachet = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE  Username = ? order by IDCard desc limit 1";

    private static final String HAS_CARDS_QUERY ="select * from collections where ID_Card = ?, ID_User = ?";

    private static final String SEE_GIFTED_QUERY="select * from accesses where Username=?";

    private static final String INSERT_GIFTED_QUERY="insert into accesses (Username) VALUES(?)";

    private static final String insert_collection="insert into collections (ID_CARD,Username) values ((select id from catalog order by rand() limit 1),?) ON DUPLICATE KEY UPDATE quantity=quantity+1;";

    private FacadeImplements f= new FacadeImplements();

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;


    /**
     * Inserts a card in user's collection
     * @param user type User. Indicates user who will have the card
     * @param card type Card. Indicates card that is going to be inserted
     * @return true if the card is successfully inserted in user's collection, false otherwise
     */
    public boolean insert(User user, Card card) {
        conn = null;
        try {
            conn = connector.createConnection();
            String query2 = INSERT_QUERY + "("+user.getUsername()+", "+card.getId()+")";
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
    public CollectionOwn getCollentionOwn(User user){
        Map<Card,Integer> c=new HashMap<>();
        CollectionOwn collectionOwn;
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
                        c.put(card,result.getInt("quantity"));
                //to do: farla diventare mappa con quantità
            }
            return new CollectionOwn(user, c);
        }catch (SQLException e){
            return null;
        }
    }

    /**
     * Inserts in user's collection a card chosen randomly from the catalog
     * @param user type User. Indicates user who is going to receive the card
     * @return Card chosen randomly from the catalog
     */
    @Override
    public Card createRandomCard(User user){
        try{
            Card card;
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(insert_collection);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            card = get_last_card(user);
            return card;
        }catch (SQLException e){
            System.out.println(e.toString());
            return null;
        }

    }

    /**
     * Gifts five new cards to user when they log to the platform today.
     * Cards can be gifted once a day
     * <p>
     *     Checks in the database table accesses if the user has logged to the platform today. If it's their first
     *     time they logged for that day, the user receives new cards and the user's username is inserted to
     *     the table
     * </p>
     * @param user type User. Indicates user that has to be checked if they already logged today
     */
    public void giftCard(User user){
        int nCards=5;
        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(SEE_GIFTED_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();

            if(!result.next()){
                for(int i=0;i<nCards;i++){
                    createRandomCard(user);
                }
                preparedStatement = conn.prepareStatement(INSERT_GIFTED_QUERY);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.execute();
                preparedStatement.setString(1,user.getUsername());

                }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
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



    /**
     * Allows the logged user to find certain cards in their collection,
     * through the filters of the search bar.
     * <p>
     *      depending to parameters specified, the original query is completed in such a way that
     *      it can returned the requested cards
     * </p>
     * @param user type User.Indicates logged user
     * @param name  a String.Indicates name of the card searched.
     * @param category  a String.Indicates category of the cards searched.
     * @param classCard a String.Indicates class of the cards searched .
     * @param typeCard  a String.Indicates type of the cards searched.
     * @return ArrayList<Card> that contains the requested cards from logged user.
     * @throws SQLException exception caused by database error.
     */
    public ArrayList<Card> filters (User user, String name, String category , String classCard, String typeCard )throws SQLException{

        ArrayList<Card> list= new ArrayList<Card>();
        int j=2;
        String search_cards="select * from collections inner join catalog on (collections.ID_Card=catalog.ID) WHERE Username=?";
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(f.completeQuery(search_cards,name,category,classCard,typeCard));
            preparedStatement.setString(1, user.getUsername());
            f.setQuery(j,preparedStatement,name,category,classCard,typeCard);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                for (int i = 0; i < result.getInt("quantity"); i++) {
                    Card answer = new Card(result.getInt("ID"),
                            result.getString("Category"),
                            result.getString("Class"),
                            result.getInt("Lvl"),
                            result.getString("Rarity"),
                            result.getString("CardType"),
                            result.getString("CardName"),
                            result.getString("CardDescription"));
                    list.add(answer);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return list;
    }





    @Override
    public ArrayList<Card> openSachet(User user){
        ArrayList<Card> c = new ArrayList<Card>();

        for(int i=0; i<6; i++){                 // TODO: cambiare il 6 per una costante globale (no hardcode).
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
