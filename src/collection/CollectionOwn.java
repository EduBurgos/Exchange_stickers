package collection;

import dao.CollectionOwnDaoImpl;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/** Class that represents the collection owned by the user
 * @see User
 * @see Card
 */
public class CollectionOwn {
    private User owner;
    private Map<Card,Integer> cardsOwn;


    public CollectionOwn(User owner, Map<Card,Integer> cardsOwn) {
        this.owner = owner;
        this.cardsOwn = cardsOwn;
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
    public void setCardsOwn(Map cardsOwn) {
        this.cardsOwn = cardsOwn;
    }

    public Map<Card,Integer> searchCard(String search)
    {
        Map<Card,Integer> result=new HashMap<>();
        for (Card c:cardsOwn.keySet()) {
            if(c.getNome().contains(search))
            {
                result.put(c,cardsOwn.get(c));
            }
        }
        return result;
    }

    public  boolean searchCardById(int id)
    {
        for (Card c:cardsOwn.keySet()) {
            if(c.getId()==id)
            {
                return true;
            }
        }
        return false;
    }
    /*public int getlastQt()
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
    }*/
}
