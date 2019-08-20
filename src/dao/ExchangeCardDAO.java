package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ExchangeCardDAO {
    //CRUD
    /*The public is redundant*/
     void create(User user, ArrayList<Card> cardown, ArrayList<Card> cardwanted) throws SQLException;

     boolean marketExchange(Exchange exchangeCard, User user, ArrayList<Card> cardsmarket);

     Exchange getExchange(int id_trans) throws SQLException;

     //void update() throws SQLException;

     void delete(int id_trans) throws SQLException;

     ArrayList<Exchange> getAllExchange() throws SQLException;

}
