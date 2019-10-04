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

    private static final String remove_card_buyer = "update collections set id_user = (select ID from users where Username = ? ) where IDCardColl in ";
    private static final String remove_card_seller = "update collections set In_Market = false, id_user = (select ID_User from exchange where id_trans = ?) where IDCardColl in ";
    private static final String flag_complete ="update exchanges set trans_comp = true where id_trans = ? ";

    private static final String get_exchange ="select exchanges.* , cards_own.cardId from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=?";
    private static final String get_cardWanted="select card_wanted.cardId as card_wanted from card_wanted where Id_trans=?";
    private static final String get_all_exchange ="select * from exchanges";

    private static final String delete_exchange = "delete from exchanges where id_trans = ?";


    private static final String view_catalog = "select * from catalog";

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
    public boolean marketExchange(Exchange exchangeCard, User user, ArrayList<Card> cards) {
        conn = null;

        String options ="(";

        for (Card c: cards){
            if (c.getIdColl() == 0) break;
            options = options+c.getIdColl()+",";
        }
        options = options.substring(0, options.length()-1);
        options = options+");";

        String query1 = remove_card_buyer+options;

        /**c is int because we interting only idcardcoll*/
        for (int c: exchangeCard.getId_card_owm()){
            if (c == 0) break;
            options = options+c+",";
        }
        options = options.substring(0, options.length()-1);
        options = options+");";

        String query2 = remove_card_seller+options;
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
            return new Exchange(id_trans, result.getInt("id_user"), cardown, cardwanted, result.getBoolean("trans_comp"));
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

    /**Retrun all Exchanges*/
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
                int[] cardown = toInts(result.getBytes("id_card_owm"));
                int[] cardwanted = toInts(result.getBytes("id_card_wanted"));
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

    public void view_catalog(){
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
    }





    /**
     * Combert int to bytes
     */
    private byte[] tobytes(int[] cardsId) {
        byte[] joined_bytes = new byte[20]; // 5 ints
        for (int i = 0; i < 5; i++) {
            int c = cardsId[i];
            for (int j = 0; j < 4; j++) {
                joined_bytes[j + 4 * i] = (byte) c;
                c = c >> 8;
            }
        }
        return joined_bytes;
    }

    /**
     * Combert bytes to int
     */
    private int[] toInts(byte[] cardsBlob) {
        int[] cardIds = new int[5];
        for (int i = 0; i < 5; i++) {
            int itemp = 0;
            for (int j = 0; j < 4; j++) {
                itemp = itemp << 8;
                itemp += cardsBlob[3 - j + 4 * i] & 0xFF;
            }
            cardIds[i] = itemp;
        }
        return cardIds;
    }
}
