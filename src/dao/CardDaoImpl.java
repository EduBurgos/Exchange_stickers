package dao;

import collection.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    //esplicitazione query
    /**query used to insert a new card in DB*/
    private static final String INSERT_QUERY = "INSERT INTO catalog (ID, Category,Class,Lvl,Rarity,CardType,CardName,CardDescription) VALUES (?,?,?,?,?,?,?,?,?)";
    /**query used to delete a card in DB*/
    private static final String DELETE_QUERY = "DELETE FROM catalog WHERE ID = ?";
    /** query used to update a card in DB*/
    private static final String UPDATE_QUERY = "UPDATE catalog SET ID=? , Category=? , Class=? , Lvl=?, Rarity=?,CardType=?, CardName=?, CardDescription=?) WHERE ID = ?";
    /**query used to find cards by category in DB*/
    private static final String FIND_BY_CATEGORY = "SELECT * FROM catalog WHERE Category = ?";
    /**query used to find cards by their name in DB*/
    private static final String FIND_BY_NAME = "SELECT * FROM catalog WHERE CardName = ?";
    /**query used to find a card ny its ID in DB*/
    private static final String FIND_BY_ID = "SELECT * FROM catalog WHERE ID = ?";
    /**query used to find cards by class in DB*/
    private static final String FIND_BY_CLASS = "SELECT * FROM catalog WHERE Class = ?";
    /**query used to find all cards in DB*/
    private static final String FIND_ALL= "SELECT * FROM catalog";



    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    /**
     * Method used to insert a card in DB
     * @param card card which is going to be added
     * @return true if the card is added, false if not
     * @throws SQLException
     */
    @Override
    public boolean insert(Card card) throws SQLException {
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, card.getId());
            preparedStatement.setString(2, card.getCategoria());
            preparedStatement.setString(3, card.getClasse());
            preparedStatement.setInt(4, card.getLivello());
            preparedStatement.setString(5, card.getRarità());
            preparedStatement.setString(6,card.getTipo());
            preparedStatement.setString(7, card.getNome());
            preparedStatement.setString(8, card.getDescrizione());
            int done = preparedStatement.executeUpdate();      //metodo che restituisce il create/modificate/cancellate di righe interessate dalla query
            if (done != 0) {
                return true;
            } else {
                return false;
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
        return false;
    }

    /**
     * Method used to delete a card in DB
     * @param card card we want to delete
     * @return true if the card has been successfully deleted, false otherwise
     * @throws SQLException
     */
    @Override
    public boolean delete(Card card) throws SQLException {
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, card.getId());
            preparedStatement.execute();
            return true;
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

    /**
     * Method used to update a card in DB
     * @param card card we want to update
     * @return true if the card has  been successfully updated, false otherwise
     * @throws SQLException
     */
    @Override
    public boolean update(Card card) throws SQLException{
        try {
            conn= connector.createConnection();
            preparedStatement= conn.prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1,card.getId());
            preparedStatement.setString(2,card.getCategoria());
            preparedStatement.setString(3,card.getClasse());
            preparedStatement.setInt(4,card.getLivello());
            preparedStatement.setString(5,card.getRarità());
            preparedStatement.setString(6,card.getTipo());
            preparedStatement.setString(7,card.getNome());
            preparedStatement.setString(8,card.getDescrizione());
            preparedStatement.execute();
            return true;
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

}/* METODO DA MODIFICARE*/
   /* public boolean findByCategory(Card card) throws SQLException {
        Card card1=null;
        try{
        conn = connector.createConnection();
        preparedStatement = conn.prepareStatement(FINDBYCATEGORY);
        preparedStatement.setString(1, card.getCategoria());
        preparedStatement.execute();
            result = preparedStatement.getResultSet();
            if (result.next() && result != null) {
                card1 = new Card(result.getInt(2), result.getString(1), result.getString(3), result.getInt(4),result.getString(5),result.getString(6),result.getString(7),result.getString(8));
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
    }*/


    /**
     * Method used to find a card by its ID
     * @param id id of the card we are searching for
     * @return Card we are searching for
     * @throws SQLException
     */
    @Override
    public Card findByID(int id) throws SQLException {
        Card card = null;
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            if (result.next() && result != null) {
                card = new Card(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8));
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
        return card;
    }

public ArrayList<Card> findAll() throws SQLException {
        ArrayList<Card> allCards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_ALL);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                    allCards.add(new Card(result.getInt(1),result.getString(2),result.getString(3), result.getInt(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8)));

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
        return allCards;
    }

    @Override
    public List<Card> findByClass(String cardClass) throws SQLException {
        return null;
    }

    @Override
    public Card findByName(String cardName) throws SQLException {
        return null;
    }


    @Override
    public List<Card> findByType() throws SQLException {
        return null;
    }
}
