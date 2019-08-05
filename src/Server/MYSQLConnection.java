package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLConnection {
    private static MYSQLConnection ourInstance = new MYSQLConnection();

    public static MYSQLConnection getInstance() {
        return ourInstance;
    }

    private MYSQLConnection(){

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/cardwebapplication","root","estefania93");
//here sonoo is database name, root is username and password

            } catch(SQLException e){
                System.out.println(e);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}
