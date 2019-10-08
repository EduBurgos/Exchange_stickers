package dao;

import collection.Card;
import com.mysql.jdbc.Statement;
import userSide.Exchange;
import userSide.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ExchangeCardDAOImpl implements ExchangeCardDAO {
    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    private static final String insert_transaction_query = "insert into exchanges values (id_trans, (select id from users where Username = ?), false)";
    private static final String insert_cardown_query = "insert into cards_own values (?,?)";
    private static final String insert_cardWanted_query = "insert into cards_wanted values (?,?)";

    private static final String  switchpeople = "update collections username = ? WHERE idCard=?";
    private static final String remove_card_seller = "update collections set In_Market = false, id_user = (select ID_User from exchange where id_trans = ?) where IDCardColl in ";
    private static final String flag_complete ="update exchanges set trans_comp = true where id_trans = ? ";

    private static final String get_exchange ="select exchanges.* , cards_own.cardId from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=?";
    private static final String get_cardWanted="select card_wanted.cardId as card_wanted from card_wanted where Id_trans=?";
    private static final String get_all_exchange ="select * from exchanges";

    private static final String delete_exchange = "start transaction;" +
                                                        "delete from exchanges where id_trans=?" +
                                                    "rollback;" +
                                                    "commit;";


    //private static final String view_catalog = "select * from catalog";

    @Override
    public void create(User user, ArrayList<Card> cardown, ArrayList<Card> cardwanted) throws SQLException {
        conn = null;


        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(insert_transaction_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();

            ResultSet rs=preparedStatement.getGeneratedKeys();

            if (rs.next())
            {
                for (Card c: cardwanted) {
                    preparedStatement=conn.prepareStatement(insert_cardown_query);
                    preparedStatement.setInt(1,rs.getInt(1));
                    preparedStatement.setInt(1,c.getId());
                    preparedStatement.execute();
                }
                for ( Card c: cardown) {
                    preparedStatement=conn.prepareStatement(insert_cardWanted_query);
                    preparedStatement.setInt(1,rs.getInt(1));
                    preparedStatement.setInt(1,c.getId());
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
    public boolean marketExchange(Exchange exchangeCard, User user, ArrayList<Card> cardsmarket) {
        return false;
    }


    /* public boolean marketExchange(Exchange exchangeCard) {
        conn = null;



        String query1 = remove_card_buyer+options;
        for (String c: exchangeCard.getUsername()){
            if (c == 0) break;
            options = options+c+",";
        }
        options = options.substring(0, options.length()-1);
        options = options+");";
        for (String c: exchangeCard.getUsername_offerente()){
            if (c == 0) break;
            options = options+c+",";
        }
        options2 = options2.substring(0, options2.length()-1);
        options2= options2+");";
        String query2 = remove_card_buyer+options2;


        String query3 = flag_complete;


        String queryComplete = query1+query2+query3;
        try{
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(queryComplete);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, exchangeCard.getId_trans());
            preparedStatement.setInt(3, exchangeCard.getId_trans());

            int done = preparedStatement.executeUpdate();      //metodo che restituisce il create/modificate/cancellate di righe interessate dalla query
            if (done != 0) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    } */

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
                cardown[counter] = result.getInt("card_own");
                counter++;

            }
            preparedStatement = conn.prepareStatement(get_cardWanted);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            counter=0;
            while (result.next()) {
                cardwanted[counter] = result.getInt("card_wanted");
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

    /**Retrun all Exchanges da rifare
    @Override
    public ArrayList<Exchange> getAllExchange() throws SQLException {
        conn=null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(get_all_exchange);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();

            ArrayList<Exchange> allExchange = new ArrayList<>();

            while (result.next() && result != null) {


                allExchange.add(new Exchange(result.getInt("id_trans"), result.getInt("id_user"), cardown, cardwanted, false));
                System.out.println("cardown = "+ Arrays.toString(cardown));
                System.out.println("cardwanted = "+ Arrays.toString(cardwanted));
            }
            return allExchange;

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
        return null;
    } */

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

    @Override
    public ArrayList<Exchange> getAllExchange() throws SQLException {
        return null;
    }

    /*public void view_catalog(){
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(view_catalog);
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
    }*/
}
