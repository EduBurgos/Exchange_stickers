package dao;

import userSide.Trattativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrattativaDaoImpl implements TrattativaDao{
    /**query used to insert a new exchange in DB*/
    private static final String CREATE_QUERY = "INSERT INTO CardExchange (Offerer, Receiver, Offer, Counteroffer) VALUES (?,?,?,?)";
    /** query used to select an exchange in DB*/
    private static final String FIND_TRATTATIVA_QUERY = "SELECT * FROM CardExchange WHERE Offerer=?, Receiver=?, Offer=?, Counteroffer=?";
    /** query used to update an exchange in DB*/
    private static final String UPDATE_QUERY = "UPDATE CardExchange SET Offerer=? , Receiver=? , Offer=? , Counteroffer=? WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";
    /** query used to delete an exchange in DB*/
    private static final String DELETE_QUERY = "DELETE FROM CardExchange WHERE Offerer=? , Receiver=? , Offer=? , Counteroffer=?";

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
    public Trattativa find() throws SQLException {
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
}
