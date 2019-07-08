package dao;

import collection.Card;
import userSide.User;

import java.sql.SQLException;
import java.util.List;

public interface CardDao {
    //tutte le query che faremo
    boolean insert(Card card) throws SQLException;
    Card findByID(int id) throws  SQLException;
    List<Card> findByClass(String cardClass) throws SQLException;
    Card findByName(String cardName) throws SQLException;
    boolean update(Card card) throws SQLException;
    boolean delete(Card card) throws SQLException;
    List<Card> findByType() throws SQLException;

}
