package collection;

import dao.CollectionOwnDaoImpl;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/** Class that represents the collection owned by the user
 * @see User
 * @see Map<Card,Integer>
 */
public class CollectionOwn {
    private User owner;
    private TreeMap<Card,Integer> cardsOwn;


    public CollectionOwn(User owner, TreeMap<Card,Integer> cardsOwn) {
        this.owner = owner;
        this.cardsOwn = cardsOwn;
    }
    public CollectionOwn()
    {
        this.cardsOwn=new TreeMap<>();
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public Map<Card, Integer> getCardsOwn() {
        return cardsOwn;
    }
    public void setCardsOwn(TreeMap<Card,Integer> cardsOwn) {
        this.cardsOwn = cardsOwn;
    }
    public int getlastQt()
    {
        return cardsOwn.lastEntry().getValue();
    }
    public Card getLastCard()
    {
        return cardsOwn.lastKey();
    }
    public boolean insert(Card card,int quantity) throws SQLException {
        cardsOwn.put(card,quantity);
        CollectionOwnDaoImpl collectionOwnDao=new CollectionOwnDaoImpl();
        return collectionOwnDao.insert(card,owner,quantity);
    }
    public TreeMap<Card, Integer> readCollection (User user) throws SQLException
    {
        CollectionOwnDaoImpl collectionOwnDao=new CollectionOwnDaoImpl();
        return collectionOwnDao.create_collection(user);
    }
}
