package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory {
    /** class driver */
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    private static MySQLDAOFactory ourInstance = new MySQLDAOFactory();
    public static MySQLDAOFactory getInstance() {
        return ourInstance;
    }
    /**
     * Method used to create a connection to MYSQL DB
     * @return object Connection.
     */
    public Connection createConnection() {
        Connection conn = null;
        try {

            Class.forName(DRIVER);
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cardwebapplication?useSSL=false&user=root&password=abcd");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}