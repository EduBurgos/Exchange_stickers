package dao;

import collection.Card;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CollectionOwnDao {
    //CRUD
    ArrayList<Card> getCollentionOwn(User user);
    //boolean insert(Card card,User user,int quantity) throws SQLException;
    boolean update() throws SQLException;
    boolean delete() throws SQLException;

    /*Potenzialmente sara refattorizzato , decidere se pasare parte o completamente loggetto*/
    Card createRandomCard(User user) throws  SQLException;

    Card get_last_card(User user);
    ArrayList<Card> openSachet(User user);


}
