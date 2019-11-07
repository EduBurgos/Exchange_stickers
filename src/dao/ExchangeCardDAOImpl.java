package dao;

import collection.Card;
import collection.CollectionOwn;
import com.mysql.jdbc.Statement;
import userSide.Exchange;
import userSide.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExchangeCardDAOImpl implements ExchangeCardDAO {
    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    private static final String insert_transaction_query = "INSERT INTO exchanges (username) VALUES (?)";
    private static final String insert_cardown_query = "insert into cards_own(id_trans,cardId) values (?,?)";
    private static final String insert_cardWanted_query = "insert into cards_wanted(id_trans,cardId) values (?,?)";

    private static final String switchpeople = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;"+
                                                 "SET AUTOCOMMIT=0;" +
                                                    "start TRANSACTION;" +
                                                        "select * from collections where ID_Card=? AND USERNAME=?;" +
                                                        " update collections" +
                                                            "SET  USERNAME=? WHERE ID_CARD=? AND USERNAME=?;" +
                                                    "commit;";
    private static final String flag_complete ="start TRANSACTION;" +
                                                    "    select * from exchanges where id_trans=?;" +
                                                    "    update exchanges" +
                                                    "SET  USERNAME_Offer=? , trans_comp =? WHERE id_trans=?;" +
                                                "commit; ";

    private static final String get_exchange ="select exchanges.* , cards_own.cardId from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=?  ";
    private static final String get_cardWanted="select exchanges.* , cards_wanted.cardId from (exchanges join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=? ";
    private static final String get_all_exchange ="select * from exchanges";

    private static final String delete_exchange = "SET AUTOOMMIT=0" +
                                                    "start transaction;" +
                                                        "delete from exchanges where id_trans=?" +
                                                    "commit;";
    //private static final String view_catalog = "select * from catalog";
    // query trova trattativa secondo nome della carta
    private static final String SEARCH_BY_NAME_CARD="SELECT exchanges.*, cards_own.cardId from (exchanges natural join cards_own) join catalog ON cards_own.cardId=catalog.ID WHERE catalog.CardName=?";
    private static final String SEARCH_BY_CATEGORY="exchanges.*, cards_own.cardId from (exchanges natural join cards_own) join catalog ON cards_own.cardId=catalog.ID WHERE catalog.Category=? ";
    private static final String SEARCH_BY_CLASS=" exchanges.*, cards_own.cardId from (exchanges natural join cards_own) join catalog ON cards_own.cardId=catalog.ID WHERE catalog.Class=?";
    private static final String SEARCH_BY_TYPE="exchanges.*, cards_own.cardId from (exchanges natural join cards_own) join catalog ON cards_own.cardId=catalog.ID WHERE catalog.CardType=?";



    @Override
    public void create(User user, ArrayList<Card> cardown, ArrayList<Card> cardwanted) throws SQLException {
        conn = null;


        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(insert_transaction_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();

            result=preparedStatement.getGeneratedKeys();

            if (result.next() && result!=null)
            {
                for (Card c: cardown) {
                    preparedStatement=conn.prepareStatement(insert_cardown_query);
                    preparedStatement.setInt(1,result.getInt(1));
                    preparedStatement.setInt(2,c.getId());
                    preparedStatement.execute();
                }
                for ( Card d: cardwanted) {
                    preparedStatement=conn.prepareStatement(insert_cardWanted_query);
                    preparedStatement.setInt(1,result.getInt(1));
                    preparedStatement.setInt(2,d.getId());
                    preparedStatement.execute();
                }
            }

        } catch (SQLException e) {
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
    public boolean marketExchange(Exchange exchangeCard) {
        conn = null;
        //cancello carte presenti in entrambe le liste perchè è inutile scambaire due carte uguali
        for (int i :exchangeCard.getId_card_owm()) {
            for (int k: exchangeCard.getId_card_wanted()) {
                if(i==k)
                {
                    exchangeCard.getId_card_wanted()[i]=0;
                    exchangeCard.getId_card_owm()[i]=0;
                }
            }
        }
        try {
            //setto flag completato = 1 per evitare che altri utenti accettino lo stesso scambio
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(flag_complete);
            preparedStatement.setInt(1,exchangeCard.getId_trans());
            preparedStatement.setString(2,exchangeCard.getUsername_offerente());
            preparedStatement.setInt(3,exchangeCard.getId_trans());
            preparedStatement.execute();

            //da al mio utente le carte che vuole
            for (int i: exchangeCard.getId_card_wanted()) {
                //se è già nella collezione incremento la quantità
                preparedStatement = conn.prepareStatement(switchpeople);
                preparedStatement.setInt(1,i);
                preparedStatement.setString(2,exchangeCard.getUsername_offerente());
                preparedStatement.setString(3,exchangeCard.getId_user());
                preparedStatement.setInt(4,i);
                preparedStatement.setString(5,exchangeCard.getUsername_offerente());
                preparedStatement.execute();
            }
            //da all'offerente le carte vendute
            for (int i: exchangeCard.getId_card_owm()) {
                preparedStatement = conn.prepareStatement(switchpeople);
                preparedStatement.setInt(1,i);
                preparedStatement.setString(2,exchangeCard.getId_user());
                preparedStatement.setString(3,exchangeCard.getUsername_offerente());
                preparedStatement.setInt(4,i);
                preparedStatement.setString(5,exchangeCard.getId_user());
                preparedStatement.execute();
            }

        }
        catch (SQLException e)
        {

        }
            return false;

    }

    /**Retrun a exchange*/
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(get_exchange);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            int [] cardown =new int[5];
            int[] cardwanted= new int[5];
            int counter=0;

            while (result.next()) {
                cardown[counter] = result.getInt("cardId");
                counter++;

            }
            preparedStatement = conn.prepareStatement(get_cardWanted);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            counter=0;
            while (result.next()) {
                cardwanted[counter] = result.getInt("cardId");
                counter++;
            }
            return new Exchange(id_trans, result.getString("username"), cardown,cardwanted, result.getBoolean("trans_comp"),result.getString("username_offer"));
        } catch (SQLException e) {
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
            return null;
        }
    }

    /**Return all available exchanges */
    @Override
    public ArrayList<Exchange> getAllExchange(User user) throws SQLException {
        conn=null;
        ArrayList<Exchange> allExchange = new ArrayList<>();
        try {
            int i=1;
            int counter=0;
            int[]cardown=new int[5];
            int[] cardwanted=new int[5];
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(get_exchange);
            preparedStatement.setInt(1, i);
           // preparedStatement.setString(2, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while(result!=null && result.next()) {
                if(!result.getBoolean("trans_comp") && !result.getString("username").equals(user.getUsername())) {
                result.previous();
                    while (result.next()) {
                        cardown[counter] = result.getInt("cardId");
                        counter++;

                    }

                    counter = 0;
                    preparedStatement = conn.prepareStatement(get_cardWanted);
                    preparedStatement.setInt(1, i);
                    // preparedStatement.setString(2, user.getUsername());
                    preparedStatement.execute();
                    result = preparedStatement.getResultSet();

                    while (result.next()) {
                        cardwanted[counter] = result.getInt("cardId");
                        counter++;

                    }
                    preparedStatement = conn.prepareStatement(get_cardWanted);
                    preparedStatement.setInt(1, i);
                    //  preparedStatement.setString(2, user.getUsername());
                    preparedStatement.execute();
                    result = preparedStatement.getResultSet();
                    while (result.next() && result != null) {
                        allExchange.add(new Exchange(result.getInt("id_trans"), result.getString("username"), cardown, cardwanted, false, result.getString("username_offer")));
                        System.out.println("cardown = " + Arrays.toString(cardown));
                        System.out.println("cardwanted = " + Arrays.toString(cardwanted));
                    }
                }
                i++;
                preparedStatement = conn.prepareStatement(get_exchange);
                preparedStatement.setInt(1, i);
              //  preparedStatement.setString(2, user.getUsername());
                preparedStatement.execute();
                result = preparedStatement.getResultSet();
                counter=0;
                cardown=new int[5];
                cardwanted=new int[5];

            }

        } catch (SQLException e) {
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
        return allExchange;
    }

    @Override
    public void delete(int id_trans) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(delete_exchange);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
        } catch (SQLException e) {
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

    /**..............METODI PER CERCARE LE TRATATTIVE**/
    /**Metodo che mi trova tratativa/e secondo il nome della carta*/
    public ArrayList<Exchange>findTByNameCard(User user, String nameCard) throws SQLException{
        conn=null;
        Exchange ex=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(SEARCH_BY_NAME_CARD);
            preparedStatement.setString(1, nameCard);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while(result!=null && result.next()) {
                if(!result.getBoolean("trans_comp") && !result.getString("username").equals(user.getUsername())) {
                    result.previous();
                    while (result.next()) {
                        ex=getExchange(result.getInt("id_trans"));
                        answer.add(ex);
                    }
                }
            }

            return answer;

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
        return null;
    }

    /**Metodo che mi trova tratativa/e secondo la categoria*/
    public ArrayList<Exchange>findTByCategory(User user, String category) throws SQLException{
        conn=null;
        Exchange ex=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(SEARCH_BY_CATEGORY);
            preparedStatement.setString(1, category);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while(result!=null && result.next()) {
                if(!result.getBoolean("trans_comp") && !result.getString("username").equals(user.getUsername())) {
                    result.previous();
                    while (result.next()) {
                        ex=getExchange(result.getInt("id_trans"));
                        answer.add(ex);
                    }
                }
            }

            return answer;

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
        return null;
    }

    /**Metodo che mi trova tratativa/e secondo la classe della carta*/
    public ArrayList<Exchange>findTByClassCard(User user, String classCard) throws SQLException{
        conn=null;
        Exchange ex=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(SEARCH_BY_CLASS);
            preparedStatement.setString(1, classCard);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while(result!=null && result.next()) {
                if(!result.getBoolean("trans_comp") && !result.getString("username").equals(user.getUsername())) {
                    result.previous();
                    while (result.next()) {
                        ex=getExchange(result.getInt("id_trans"));
                        answer.add(ex);
                    }
                }
            }

            return answer;

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
        return null;
    }

    /**Metodo che mi trova tratativa/e secondo il tipo della carta*/
    public ArrayList<Exchange>findTByTypeCard(User user, String typeCard) throws SQLException{
        conn=null;
        Exchange ex=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(SEARCH_BY_TYPE);
            preparedStatement.setString(1, typeCard);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while(result!=null && result.next()) {
                if(!result.getBoolean("trans_comp") && !result.getString("username").equals(user.getUsername())) {
                    result.previous();
                    while (result.next()) {
                        ex=getExchange(result.getInt("id_trans"));
                        answer.add(ex);
                    }
                }
            }

            return answer;

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
        return null;
    }

/**..................FINE METODI PER LA RICERCA DI TRATTATIVE**/


}
