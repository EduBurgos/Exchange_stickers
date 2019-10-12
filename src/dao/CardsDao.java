package dao;

import collection.Card;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CardsDao {
    //tutte le query che faremo
    boolean insert(Card card) throws SQLException;
    Card findByID(int id) throws  SQLException;
    ArrayList<Card> findByClass(String cardClass) throws SQLException;
    ArrayList<Card> findByName(String cardName) throws SQLException;
    boolean update(Card card) throws SQLException;
    boolean delete(Card card) throws SQLException;
    ArrayList<Card> findByType(String cardType) throws SQLException;

}
