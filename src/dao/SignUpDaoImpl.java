package dao;

import Server.MYSQLConnection;
import servlets.SignUpServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpDaoImpl implements SignUpDao{


    /** non utile, c'è già il metodo in user **/
    static Connection con;
    static PreparedStatement ps;    /**Prepare the Query*/

    @Override
    public int insertSignUpServlet(SignUpServlet s) {
        int status = 0;

        try{
            con = MYSQLConnection.getInstance()); /** NON riconosce getCon() perchè è getIstance() **/
            ps = con.prepareStatement("insert into user values (?,?,?)"); /**DA SISTEMARE CAMPI NON COMPATIVILI*/

            ps.setString(1, s.getName());
            ps.setString(2, s.getSurname());
            ps.setString(3, s.getUserName());

            status = ps.executeUpdate();
            con.close();;

        }catch (Exception e){
            System.out.println(e);
        }

        return status;
    }

    @Override
    public SignUpServlet getSignUpServlet(String name, String surname, String userName) {

        SignUpServlet s = new SignUpServlet();

        try{
            /** ma abbiamo già la query ps in user **/
            con = MYSQLConnection.getInstance();
            ps = con.prepareStatement("select * from user where Username = ?");     /**DA SISTEMARE CAMPI NON COMPATIBILI*/
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                s.setName(rs.getString(1));
                s.setSurname(rs.getString(2));
                s.setUserName(rs.getString(3));
            }


        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
