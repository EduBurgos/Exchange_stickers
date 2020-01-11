package dao;

import collection.Card;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardsDaoImpl implements CardsDao {

    //esplicitazione query
    /**query used to insert a new card in DB*/
    private static final String INSERT_QUERY = "INSERT INTO catalog (ID, Category,Class,Lvl,Rarity,CardType,CardName,CardDescription) VALUES (?,?,?,?,?,?,?,?)";
    /**query used to delete a card in DB*/
    private static final String DELETE_QUERY = "DELETE FROM catalog WHERE ID = ?";
    /** query used to update a card in DB*/
    private static final String UPDATE_QUERY = "UPDATE catalog SET ID=? , Category=? , Class=? , Lvl=?, Rarity=?,CardType=?, CardName=?, CardDescription=?) WHERE ID = ?";
    /*** query used to find a card ny its ID in DB*/
    private static final String FIND_BY_ID = "SELECT * FROM catalog WHERE ID = ?";

    /*** query used to find all cards in DB*/
    private static String FIND_ALL = "SELECT * FROM catalog ";


    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;
    private FacadeImplements f=new FacadeImplements();

    /**
     * Inserts a card in DB
     * @param card type Card. Indicates card which is going to be added
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
     * Deletes a card in DB
     * @param card type Card.Indicates card which is going to be delete
     * @return true if the card has been successfully deleted, false otherwise
     * @throws SQLException
     */
    @Override
    public boolean delete(Card card) {
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
     * Updates a card in DB
     * @param card type Card. Indicates card that has to be updated
     * @return true if the card has  been successfully updated, false otherwise
     * @throws SQLException
     */
    @Override
    public boolean update(Card card){
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
    }

    /**
     * Allows the logged user to find certain cards in the catalog,
     * through the filters of the search bar.
     * <p>
     *      depending to parameters specified, the original query is completed in such a way that
     *      it can returned the requested cards
     * </p>
     *
     * @param nameCard  a String.Indicates name of the card searched.
     * @param category  a String.Indicates category of the cards searched.
     * @param classCard a String.Indicates class of the cards searched .
     * @param typeCard  a String.Indicates type of the cards searched.
     * @return ArrayList<Card> that contains the requested cards from logged user.
     * @throws SQLException exception caused by database error.
     */

   public ArrayList<Card> filterCatalog(String nameCard, String category,String classCard, String typeCard)throws SQLException{

       ArrayList<Card> list= new ArrayList<Card>();
        String search=FIND_ALL.concat("where true");
       int j=1;
              try {
           conn = connector.createConnection();
           preparedStatement = conn.prepareStatement(f.completeQuery(search,nameCard,category,classCard,typeCard));
           f.setQuery(j,preparedStatement,nameCard,category,classCard,typeCard);
           preparedStatement.execute();
           result = preparedStatement.getResultSet();
           while (result.next() && result != null) {

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
     * Finds a card by its ID in the catalog
     * @param id a int. Indicates id of the card searched
     * @return Card that has to be searched
     * @throws SQLException
     */
    @Override
    public Card findByID(int id){
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

    /**
     * Finds all the cards of the catalog
     * @return ArrayList<Card> that contains all the cards that belong to the catalog
     */
    public ArrayList<Card> findAllGeneric() {
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
