package dao;


import collection.Card;
import userSide.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    /** Query used to add a new user*/
    private static final String CREATE_QUERY = "INSERT INTO users (Username, NameUser, Surname, mail, Pass)"+"VALUES";
    /** Query used to read a single user */
    private static final String READ_QUERY = "SELECT * FROM users WHERE Username = ?";
    /** Query used to read all users */
    private static final String READ_ALL_QUERY = "SELECT id, first_name, last_name FROM customers";
    /** Query used to update a single user. */
    private static final String UPDATE_QUERY = "UPDATE users SET Username=? , NameUser=? , Surname=? , Mail=? WHERE Username = ?";
    /** Query used to delete a single user. */
    private static final String DELETE_QUERY = "DELETE FROM users WHERE Username = ?";
    /** Query used to read a single user. */
    private static final String CHECKBYUSER_QUERY = "SELECT * FROM users WHERE Username = ?, NameUser = ?, Surname = ?, Mail = ?";

    private static final String PASSWORD_QUERY="SELECT Pass FROM users WHERE Username= ?";

    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";

    // private static final String  CHECK_UNIQ = "SELECT COUNT(Username) FROM users WHERE Username = ?";       //Verifica lesistenza del USERNAME del DB.

    /**query used to find all users in DB*/
    private static final String FIND_ALL= "SELECT * FROM users";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    /**
     * Method that adds a new user in the database
     * @param user user we want to add
     * @return true if the user has been successfully added, false if not
     * @throws SQLException
     * @throws Exception
     */
    @Override
    public boolean save(User user, String pass){
        conn=null;
        try {
            conn=connector.createConnection();

            String query = CREATE_QUERY + " ('"+user.getUsername()+"', '"+user.getNome()+"', '"+user.getCognome()+"', '"+user.getEmail()+"', '"+pass+"')";
            preparedStatement = conn.prepareStatement(query /*,Statement.RETURN_GENERATED_KEYS*/);

            /*preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPass());*/



            preparedStatement.execute();
            return true;
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
     * Method that updates a user in the database
     * @param user user we want to update
     * @return true if the user has been found and successfully updated false if not
     * @throws SQLException
     * @throws Exception
     */

    @Override
    public boolean update(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        conn=null;
        try {
            conn=connector.createConnection();
            preparedStatement = conn.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getUsername());
            //stiamo presupponendo che per l'utente no sia possibile cambiare lo username, perchè altrimenti dovremmo stare attenti che, avendo cambiato lo username, non si riuscirà a trovare come user o si confonderà con un altro
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
     * Method that deletes a user in the database
     * @param user user we want to remove
     * @return true if the user has been found and successfully removed false if not
     * @throws SQLException
     * @throws Exception
     */

    @Override
    public boolean delete(User user) throws SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn=connector.createConnection();
            preparedStatement = conn.prepareStatement(DELETE_QUERY);
            preparedStatement.setString(1, user.getUsername());
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
     * Method that checks if the user is present in the database
     * @param user user we want to find
     * @return true if the user has been found false if not
     * @throws SQLException
     * @throws Exception
     */
    @Override
    public boolean checkByUser(User user) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            if (result.next() && result != null) {
                return true; //ritorna vero se ha trovato qualcuno
            } else return false;
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
     * Method that finds all users in the database
     * @param
     * @return ArrayList which contains users in DB
     * @throws SQLException
     * @throws Exception
     */

    public ArrayList<User> findAll() throws SQLException {
        ArrayList<User> allUsers = new ArrayList<User>();
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_ALL);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            while (result.next() && result != null) {
                allUsers.add(new User((result.getString("NameUser")),
                        result.getString("Surname"),
                        result.getString("Username"),
                        result.getString("Mail")));

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
        return allUsers;
    }

    @Override
    public boolean checkUnique(User user){
        conn=null;

        try {
            conn=connector.createConnection();

            System.out.println(user.getUsername());
            String check = "SELECT COUNT(Username) FROM users WHERE Username=\"" + user.getUsername() + "\";";

            Statement s = conn.createStatement();
            result = s.executeQuery(check);
            result.next();

            //System.out.println(result.getInt("count(Username)"));

            int countUser = result.getInt("count(Username)");

            if(countUser == 0){
                return true;
            }else{
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
            /*try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }*/
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return false;
    }

    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE ID_User = (select ID from users where Username = ?)";


    public ArrayList<Card> getCollentionOwn(User user){

        ArrayList<Card> c = new ArrayList<Card>();
        String listaCarte = VIEW_COLLECTION_QUERY;
        user.getUsername();
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(listaCarte);
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
                c.add(card);
                //to do: farla diventare mappa con quantità
                //cards.put(card,result.getInt(9));
                System.out.printf("c");
            }
            return c;
        }catch (SQLException e){

        }
        return getCollentionOwn(user);
    }

    public User findByUsername(String username) throws SQLException {
        User user = null;
        conn = null;
        try {
            conn=connector.createConnection();
            preparedStatement = conn.prepareStatement(FIND_BY_USERNAME_QUERY);
            preparedStatement.setString(1,username);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            if (result.next() && result != null) {
                user = new User(result.getString("NameUser"),
                        result.getString("Surname"),
                        result.getString("Username"),
                        result.getString("mail"));
                user.setPass(result.getString("Pass"));
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
        return user;
    }
}
