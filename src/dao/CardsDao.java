package dao;

import collection.Card;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CardsDao {

    boolean insert(Card card) throws SQLException;
    Card findByID(int id) throws  SQLException;
    ArrayList<Card>filterCatalog(String nameCard,String category,String classCard, String typeCard)throws SQLException;
    boolean update(Card card) throws SQLException;
    boolean delete(Card card) throws SQLException;
    ArrayList<Card> findAllGeneric() throws SQLException;
}
