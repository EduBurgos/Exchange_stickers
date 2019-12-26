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
    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) AND Username = ?";

    private static final String INSERT_QUERY = "INSERT INTO collections (ID_Card, Username)"+"VALUES";

    private static final String new_randow_card_for_new_user ="insert into collections(ID_Card,USERNAME) values ((select id from catalog order by rand() limit 1),?)";

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
     * Method used to insert a card in user's collection
     * @param user type User. It indicates user who will have the card
     * @param card type Card. It indicates card that is going to be inserted
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
    public Map<Card,Integer> getCollentionOwn(User user){
        Map<Card,Integer> c=new HashMap<>();
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
     * Method used to gift five new cards to user when they log to the platform.
     * Cards can be gifted once a day
     * <p>
     *     Checks in the database table accesses if the user has logged to the platform today. If it's their first
     *     time they logged for that day, the user receives new cards and the user's username in  inserted to
     *     the table
     * </p>
     * @param user type User. It indicates user that has to be checked if they already logged today
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
     * Method that allows the logged user to find certain cards in his collection,
     * through the filters of the search bar.
     * <p>
     *      depending to parameters specified, the original query is completed in such a way that
     *      it can returned the requested cards
     * </p>
     * @param user type User.It indicates logged user
     * @param name  a String.It indicates name of the card searched.
     * @param category  a String.It indicates category of the card searched.
     * @param classCard a String.It indicates class of the card searched .
     * @param typeCard  a String.It indicates type of the card searched.
     * @return ArrayList<Card> that contains the requested cards from logged user.
     */
    public ArrayList<Card> filters (User user, String name, String category , String classCard, String typeCard ){

        ArrayList<Card> list= new ArrayList<Card>();
        int j=2;
        String search_cards="select * from collections inner join catalog on (collections.ID_Card=catalog.ID) WHERE Username=?";
        try {
            conn = connector.createConnection();
            if( !name.equals("")|| category!=null || !classCard.equals("") || !typeCard.equals("")) {

                if(!name.equals("")){
                    search_cards+=" AND CardName=?";
                }
                if(category!=null){
                    search_cards+=" AND Category=?";
                }
                if(!classCard.equals("")){
                    search_cards+=" AND Class=?";
                }
                if(!typeCard.equals("")){
                    search_cards+=" AND CardType =?";

                }
                preparedStatement = conn.prepareStatement(search_cards);
                preparedStatement.setString(1, user.getUsername());
                if(!name.equals("")){
                    preparedStatement.setString(j,name);
                    j++;
                }
                if(category!=null){
                    preparedStatement.setString(j,category);
                    j++;
                }
                if(!classCard.equals("")){
                    preparedStatement.setString(j,classCard);
                    j++;
                }
                if(!typeCard.equals("")){
                    preparedStatement.setString(j,typeCard);
                    j++;
                }
            }
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
