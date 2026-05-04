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
            conn = DriverManager.getConnection("jdbc:mysql://brry1vvv7qbmyjpj72yf-mysql.services.clever-cloud.com:3306/brry1vvv7qbmyjpj72yf?useSSL=true&user=uiazajh2obdv2r1l&password=yqgkJcd4J69Cn1fDgmRx");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}