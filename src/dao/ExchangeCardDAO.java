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
 ArrayList<Exchange>findTByNameCard(User user, String nameCard)throws  SQLException;
 ArrayList<Exchange>findTByCategory(User user, String category) throws  SQLException;
 ArrayList<Exchange>findTByClassCard(User user, String classCard) throws  SQLException;
 ArrayList<Exchange>findTByTypeCard(User user, String typeCard) throws  SQLException;
 ArrayList<Exchange>filters(User user, String name, String category , String classCard, String typeCard) throws SQLException;
}
