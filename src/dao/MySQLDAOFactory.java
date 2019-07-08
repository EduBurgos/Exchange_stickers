package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory {
    /** class driver */
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    /** url of the database */
    public static final String DBURL = "jdbc:mysql://localhost:3306/cardwebapplication";
    /** username used for the operations on the DB */
    public static final String USER = "root";
    /** password used for the operations on the DB */
    public static final String PASS = "abcd";

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
            Class driver_class = Class.forName(DRIVER);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);


            //conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cardwebapp?useSSL=false&user=root&password=abcd");
            conn = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return conn;
    }
}