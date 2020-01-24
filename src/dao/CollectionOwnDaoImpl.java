package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollectionOwnDaoImpl implements CollectionOwnDao {

    private static final int numbercards = 6;

    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) AND Username = ?";

    private static final String INSERT_QUERY = "INSERT INTO collections (ID_Card, Username)"+"VALUES";

    private static final String get_last_card_sachet = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE  Username = ? order by IDCard desc limit 1";

    private static final String HAS_CARDS_QUERY ="select * from collections where ID_Card = ?, ID_User = ?";

    private static final String SEE_GIFTED_QUERY="select * from accesses where Username=?";

    private static final String INSERT_GIFTED_QUERY="insert into accesses (Username) VALUES(?)";

    private static final String insert_collection="insert into collections (ID_CARD,Username) values ((select id from catalog order by rand() limit 1),?) ON DUPLICATE KEY UPDATE quantity=quantity+1;";



    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;


    /**
     * Inserts a card in user's collection
     * @param user the user who will have the card
     * @param card the card that is going to be inserted
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


    /**
     * Gets collection of a user
     * @param user the user that owns the collection
     * @return collection of the user
     */
    @Override
    public CollectionOwn getCollentionOwn(User user){
        Map<Card,Integer> c=new HashMap<>();
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
     * @param user the user that has to be checked if they already logged today
     * @return true if cards are added to the collection, false otherwise
     */
    public boolean giftCard(User user){
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
                return true;

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
        return false;
    }

    /**
     * Gets the last card chosen randomly from the catalog and given to a user
     * @param user  owner who has the last card chosen randomly
     * @return last card given randomly
     */
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
     * @param user  logged user
     * @param name  the name of the card searched.
     * @param category  the category of the cards searched.
     * @param classCard the class of the cards searched .
     * @param typeCard type of the cards searched.
     * @return requested cards from logged user.
     * @throws SQLException exception caused by database error.
     */
    public ArrayList<Card> filters (User user, String name, String category , String classCard, String typeCard ) throws SQLException{
        FacadeImplements f= new FacadeImplements();
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

   /**
     * Adds a certain number of cards chosen randomly from catalog to a list and given to a user
     * @param user the user who is going to receive the cards
     * @return cards chosen randomly
     */
   @Override
    public ArrayList<Card> openSachet(User user){
        ArrayList<Card> c = new ArrayList<Card>();

        for(int i=0; i<numbercards; i++){
            c.add(createRandomCard(user));
        }
        return c;
    }


}
