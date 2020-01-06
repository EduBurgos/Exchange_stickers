package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface CollectionOwnDao {
    //CRUD
    CollectionOwn getCollentionOwn(User user);
    //boolean insert(Card card,User user,int quantity) throws SQLException;
    boolean update() throws SQLException;
    boolean delete() throws SQLException;

    /*Potenzialmente sara refattorizzato , decidere se pasare parte o completamente loggetto*/
    Card createRandomCard(User user) throws  SQLException;
    void giftCard(User user);
    Card get_last_card(User user);
    ArrayList<Card> openSachet(User user);
    ArrayList<Card> filters (User user, String name, String category , String classCard, String typeCard ) throws SQLException;
}
