package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.Trattativa;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CollectionOwnDao {
    //CRUD
    boolean insert() throws SQLException;
    boolean find() throws SQLException;
    boolean update() throws SQLException;
    boolean delete() throws SQLException;
    ArrayList<Card> view_collection(User owner) throws SQLException;

}
