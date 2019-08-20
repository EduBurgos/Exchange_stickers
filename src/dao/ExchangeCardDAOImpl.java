package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExchangeCardDAOImpl implements ExchangeCardDAO {
    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    private static final String insert_query = "insert into exchange values (id_trans, (select id from users where Username = ?), ?, ?, false)";
    private static final String remove_card_buyer = "update collections set id_user = (select ID from users where Username = ? ) where IDCardColl in ";
    private static final String remove_card_seller = "update collections set In_Market = false, id_user = (select ID_User from exchange where id_trans = ?) where IDCardColl in ";
    private static final String flag_complete ="update exchange set trans_comp = true where id_trans = ? ";

    private static final String get_exchange ="select * from exchange where id_trans = ?";
    private static final String get_all_exchange ="select * from exchange";

    private static final String delete_exchange = "delete from exchange where id_trans = ?";

    @Override
    public void create(User user, ArrayList<Card> cardown, ArrayList<Card> cardwanted) throws SQLException {
        conn = null;

        int[] card_own = new int[5];
        int[] card_wanted = new int[5];

        for(int i=0; i<5; i++){
            card_own[i] = cardown.get(i).getidColl();
            card_wanted[i] = cardwanted.get(i).getId();
        }

        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(insert_query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, new String(tobytes(card_own)));          /**Convert bytes to string*/
            preparedStatement.setString(3, new String((tobytes(card_wanted))));

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
    public boolean marketExchange(Exchange exchangeCard, User user, ArrayList<Card> cards) {
        conn = null;

        String options ="(";

        for (Card c: cards){
            if (c.getidColl() == 0) break;
            options = options+c.getidColl()+",";
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

            if (result.next()) {
                int[] cardown = toInts(result.getBytes("id_card_onw"));
                int[] cardwanted = toInts(result.getBytes("id_card_wanted"));

                return new Exchange(id_trans, result.getInt("id_user"), cardown, cardwanted, false);
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
                int[] cardown = toInts(result.getBytes("id_card_onw"));
                int[] cardwanted = toInts(result.getBytes("id_card_wanted"));
                allExchange.add(new Exchange(result.getInt("id_trans"), result.getInt("id_user"), cardown, cardwanted, false));
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
            return null;
        }
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
