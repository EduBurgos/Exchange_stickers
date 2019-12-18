package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ExchangeCardDAO {
    //CRUD
    /*The public is redundant*/
     void create(User user, Map<Integer,Integer> cardown, Map<Integer,Integer> cardwanted) throws SQLException;

     boolean marketExchange(Exchange exchangeCard) ;


     //void update() throws SQLException;

     void delete(int id_trans) throws SQLException;
  Exchange getExchange(int id_trans) throws SQLException;
   ArrayList<Exchange> getAllExchange(User user,String Parameter) throws SQLException;
    ArrayList<Exchange>filtersexchange(User user, String name, String category , String classCard, String typeCard) throws SQLException;
    ArrayList<Exchange> exchangeToNotify(User user) throws SQLException;
    void setExchangeNotified(Exchange exchange) throws SQLException;
}
