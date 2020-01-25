package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.User;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CollectionOwnDao {
    CollectionOwn getCollentionOwn(User user);
    Card createRandomCard(User user) throws  SQLException;
    boolean giftCard(User user);
    Card get_last_card(User user);
    ArrayList<Card> filters (User user, String name, String category , String classCard, String typeCard ) throws SQLException;
}
