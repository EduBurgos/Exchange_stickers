package dao;



import userSide.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private static final String CREATE_QUERY = "INSERT INTO users (Username, NameUser, Surname, mail, Pass)"+"VALUES";

    private static final String UPDATE_QUERY = "UPDATE users SET Username=? , NameUser=? , Surname=? , Mail=? WHERE Username = ?";

    private static final String DELETE_QUERY = "DELETE FROM users WHERE Username = ?";

    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";



    private static final String FIND_ALL= "SELECT * FROM users";

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    /**
     * Adds a new user in the database
     * @param user the user who is going to be added
     * @param pass  new user's password
     * @return true if the user has been successfully added, false otherwise
     */
    @Override
    public boolean save(User user, String pass){
        conn=null;
        try {
            conn=connector.createConnection();

            String query = CREATE_QUERY + " ('"+user.getUsername()+"', '"+user.getNome()+"', '"+user.getCognome()+"', '"+user.getEmail()+"', '"+pass+"')";
            preparedStatement = conn.prepareStatement(query);

            int res= preparedStatement.executeUpdate();
            if (res >0){
                return true;
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

    /**
     * Updates a user in the database
     * @param user user who is going to be updated
     * @return true if the user has been found and successfully updated false otherwise
     * @throws SQLException exception caused by database access error
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
            //stiamo presupponendo che per l'utente no sia possibile cambiare lo username, perch?? altrimenti dovremmo stare attenti che, avendo cambiato lo username, non si riuscir?? a trovare come user o si confonder?? con un altro
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
     * Deletes a user in the database
     * @param user the user who is going to be removed
     * @return true if the user has been found and successfully removed false otherwise
     * @throws SQLException exception caused by database access error
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
     * Checks if the user is present in the database
     * @param user the  user that has to be found
     * @return true if the user has been found false otherwise
     * @throws SQLException exception caused by database access error
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
     * Finds all users in the database
     * @return users in DB
     * @throws SQLException exception caused by database access error
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

    /**
     * Checks if there is already a user with same username and/or mail
     * @param user the user that has to be checked
     * @return false if a user already exists, true otherwise
     */
    @Override
    public boolean checkUnique(User user){
        conn=null;

        try {
            conn=connector.createConnection();

            System.out.println(user.getUsername());
            System.out.println(user.getEmail());
            String check = "SELECT COUNT(Username) FROM users WHERE Username=\"" + user.getUsername() + "\";";
            String checkmail = "SELECT COUNT(Mail) FROM users WHERE Mail=\"" + user.getEmail() + "\";";

            Statement s = conn.createStatement();
            result = s.executeQuery(check);
            result.next();
            int countUser = result.getInt("count(Username)");
            result = s.executeQuery(checkmail);
            result.next();
            int countMail = result.getInt("count(Mail)");

            if(countUser == 0 && countMail == 0){
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
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return false;
    }

    private static final String VIEW_COLLECTION_QUERY = "select * from collections inner join catalog on (collections.ID_Card = catalog.ID) WHERE ID_User = (select ID from users where Username = ?)";



    /**
     * Searches user in database by their username
     * @param username the  username of the user that has to found
     * @return user found in database
     * @throws SQLException exception caused by database access error
     */
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
