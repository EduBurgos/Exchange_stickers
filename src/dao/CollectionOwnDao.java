package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.Trattativa;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public interface CollectionOwnDao {
    //CRUD
    TreeMap<Card,Integer> create_collection(User owner) throws SQLException;
    boolean insert(Card card,User user,int quantity) throws SQLException;
    boolean update() throws SQLException;
    boolean delete() throws SQLException;


}
