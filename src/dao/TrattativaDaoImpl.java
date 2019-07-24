package dao;

import userSide.Trattativa;
import userSide.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrattativaDaoImpl implements TrattativaDao{
    /**query used to insert a new exchange in DB*/
    private static final String CREATE_QUERY = "INSERT INTO CardsExchanges (Offerer, Receiver, Offer, Counteroffer, status) VALUES (?,?,?,?,?)";
    /** query used to select an exchange in DB*/
    private static final String FIND_TRATTATIVA_QUERY = "SELECT * FROM CardsExchanges WHERE Offerer=?, Receiver=?, Offer=?, Counteroffer=?";
    /** query used to update an exchange in DB*/
    private static final String UPDATE_QUERY = "UPDATE CardsExchanges SET Offerer=? , Receiver=? , Offer=? , Counteroffer=? WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";
    /** query used to delete an exchange in DB*/
    private static final String DELETE_QUERY = "DELETE FROM CardsExchanges WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";

    /** query used to accept an exchange and save in DB*/
    private static final String ACCEPT_QUERY = "UPDATE CardExchange SET status='2' WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";
    /** query used to accept an exchange and save in DB*/
    private static final String DENY_QUERY = "UPDATE CardExchange SET status='1' , WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    @Override
    public boolean create(Trattativa trattativa) throws SQLException {
        conn = null;
        String cardsOffered = trattativa.fromArraylistToString(trattativa.getCardoffer());
        String cardsCounterOffered = trattativa.fromArraylistToString(trattativa.getCounterOffer());

        try{
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, trattativa.getOfferer().getUsername());
            preparedStatement.setString(2, trattativa.getOfferer().getUsername());
            preparedStatement.setString(3, cardsOffered);
            preparedStatement.setString(4, cardsCounterOffered);
            preparedStatement.setInt(5, 0); //imposto zero  perchè presuppongo che appena creata la trattativa, non vi sia ancora risposta
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

    @Override
    public Trattativa findByTrattativa(Trattativa trattativa) throws SQLException {
        /*Trattativa found;
        UserDaoImpl utentedao1 = new UserDaoImpl();
        UserDaoImpl utentedao2 = new UserDaoImpl();
        User utente1, utente2;
        conn = null;
        String cardsOffered = trattativa.fromArraylistToString(trattativa.getCardoffer());
        String cardsCounterOffered = trattativa.fromArraylistToString(trattativa.getCounterOffer());
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(ACCEPT_QUERY);
            preparedStatement.setString(1, trattativa.getOfferer().getUsername());
            preparedStatement.setString(2, trattativa.getOfferer().getUsername());
            preparedStatement.setString(3, cardsOffered);
            preparedStatement.setString(4, cardsCounterOffered);
            preparedStatement.execute();
            preparedStatement.getResultSet();
            utente1 = utentedao1.findByUsername(result.getString(1));
            utente2 = utentedao2.findByUsername(result.getString(2));



        } catch (SQLException e) {
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
        }*/
        return null;
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
    public boolean accept(Trattativa trattativa) throws SQLException {

        conn = null;
        String cardsOffered = trattativa.fromArraylistToString(trattativa.getCardoffer());
        String cardsCounterOffered = trattativa.fromArraylistToString(trattativa.getCounterOffer());
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(ACCEPT_QUERY);
            preparedStatement.setString(1, trattativa.getOfferer().getUsername());
            preparedStatement.setString(2, trattativa.getOfferer().getUsername());
            preparedStatement.setString(3, cardsOffered);
            preparedStatement.setString(4, cardsCounterOffered);
            int done = preparedStatement.executeUpdate();
            if (done != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
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

    @Override
    public boolean deny(Trattativa trattativa) throws SQLException {
        conn = null;
        String cardsOffered = trattativa.fromArraylistToString(trattativa.getCardoffer());
        String cardsCounterOffered = trattativa.fromArraylistToString(trattativa.getCounterOffer());
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(DENY_QUERY);
            preparedStatement.setString(1, trattativa.getOfferer().getUsername());
            preparedStatement.setString(2, trattativa.getOfferer().getUsername());
            preparedStatement.setString(3, cardsOffered);
            preparedStatement.setString(4, cardsCounterOffered);
            int done = preparedStatement.executeUpdate();
            if (done != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
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
}
