package dao;

import collection.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardsDaoImpl implements CardsDao {

    //esplicitazione query
    /**query used to insert a new card in DB*/
    private static final String INSERT_QUERY = "INSERT INTO catalog (ID, Category,Class,Lvl,Rarity,CardType,CardName,CardDescription) VALUES (?,?,?,?,?,?,?,?)";
    /**query used to delete a card in DB*/
    private static final String DELETE_QUERY = "DELETE FROM catalog WHERE ID = ?";
    /** query used to update a card in DB*/
    private static final String UPDATE_QUERY = "UPDATE catalog SET ID=? , Category=? , Class=? , Lvl=?, Rarity=?,CardType=?, CardName=?, CardDescription=?) WHERE ID = ?";
    // private static final String FIND_BY_CATEGORY = "SELECT * FROM catalog WHERE Category = ?";
    private static final String FIND_BY_CATEGORY = "SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.Category=?";
    /*** query used to find cards by their name in DB*/
    // private static final String FIND_BY_NAME = "SELECT * FROM catalog WHERE CardName = ?";
    private static final String FIND_BY_NAME = "SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.CardName=?";
    /*** query used to find a card ny its ID in DB*/
    private static final String FIND_BY_ID = "SELECT * FROM catalog WHERE ID = ?";
    /*** query used to find cards by class in DB*/
    // private static final String FIND_BY_CLASS = "SELECT * FROM catalog WHERE Class = ?";
    private static final String FIND_BY_CLASS = "SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.Class=?";
    private static final String FIND_BY_TYPE = "SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.CardType=?";

    /*** query used to find all cards in DB*/
    private static final String FIND_ALL = "SELECT * FROM catalog";
    private static final String FIND_BY_CATEGORY_AND_CLASS="SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.Category=? AND c.Class=?";
    private static final String FIND_BY_CATEGORY_AND_TYPE="SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE  c.Category=? AND c.CardType=?";
    private static final String FIND_BY_CATEGORY_CLASS_AND_TYPE="SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.Category=? AND c.Class=? AND c.CardType=?";
    private static final String FIND_BY_CLASS_AND_TYPE="SELECT c.* FROM Cards_own as co inner catalogo as c on(co.cardId=c.ID) WHERE c.Class=? AND c.CardType=?";



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
                card = new Card(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));
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

    @Override
    /** Metodo per trovare le carte solo con il nome*/
    public ArrayList<Card> findByName(String cardName) throws SQLException {
        ArrayList<Card> list_cards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, cardName);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                Card card = new Card(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));

                list_cards.add(card);
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
        return list_cards;
    }

    public ArrayList<Card> findByCategory(String cardCategory) throws SQLException {
        ArrayList<Card> list_cards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_CATEGORY);
            preparedStatement.setString(1, cardCategory);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                Card card = new Card(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));

                list_cards.add(card);
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
        return list_cards;
    }

    /**
     * Metodo per la ricerca con la classe della carta
     **/
    @Override
    public ArrayList<Card> findByClass(String cardClass) throws SQLException {
        ArrayList<Card> list_cards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_CLASS);
            preparedStatement.setString(1, cardClass);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                Card card = new Card(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));

                list_cards.add(card);
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
        return list_cards;
    }

    /**Metodo per ricerca secondo tipo di carta*/
    @Override
    public ArrayList<Card> findByType(String cardType) throws SQLException {
        ArrayList<Card> list_cards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_TYPE);
            preparedStatement.setString(1, cardType);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                Card card = new Card(result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8));

                list_cards.add(card);
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
        return list_cards;
    }


    public ArrayList<Card> findAllGeneric() throws SQLException {
        ArrayList<Card> allCards = new ArrayList<Card>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_ALL);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                    allCards.add(new Card(result.getInt("ID"),
                            result.getString("Category"),
                            result.getString("Class"),
                            result.getInt("Lvl"),
                            result.getString("Rarity"),
                            result.getString("CardType"),
                            result.getString("CardName"),
                            result.getString("CardDescription")));

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


}
