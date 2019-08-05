package dao;


import userSide.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    /** Query used to add a new user*/
    private static final String CREATE_QUERY = "INSERT INTO users (Username, NameUser, Surname,mail, Pass) VALUES (?,?,?,?,?)";
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
    public boolean save(User user) throws SQLException {

        conn=null;
        try {
            conn=connector.createConnection();
            preparedStatement = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNome());
            preparedStatement.setString(3, user.getCognome());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPass());
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
     * Method that finds if the user is present in the database entering username
     * @param username username of the user we want to know if they are signed up
     * @return true if the user has been found, false if not
     * @throws SQLException
     * @throws Exception
     */

    @Override
    public User findByUsername(String username) throws SQLException {
        User user = null;
        conn = null;
        try {
            conn=connector.createConnection();
            preparedStatement = conn.prepareStatement(READ_QUERY);
            preparedStatement.setString(1,username);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            if (result.next() && result != null) {
                user = new User(result.getString(2), result.getString(3), result.getString(1), result.getString(4));
                user.setPass(result.getString(5));
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
                allUsers.add(new User((result.getString(2)) ,result.getString(3), result.getString(1), result.getString(4)));

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


}
