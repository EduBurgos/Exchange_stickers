package collection;

import dao.CollectionOwnDaoImpl;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/** Class that represents the collection owned by the user
 * @see User
 * @see Card
 */
public class CollectionOwn {
    private User owner;
    private ArrayList<Card> cardsOwn;


    public CollectionOwn(User owner, ArrayList<Card> cardsOwn) {
        this.owner = owner;
        this.cardsOwn = cardsOwn;
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public ArrayList<Card> getCardsOwn() {
        return cardsOwn;
    }
    public void setCardsOwn(ArrayList<Card> cardsOwn) {
        this.cardsOwn = cardsOwn;
    }

    public ArrayList<Card> searchCard(String search)
    {
        ArrayList<Card> result=new ArrayList<>();
        for (Card c:cardsOwn) {
            if(c.getNome().contains(search))
            {
                result.add(c);
            }
        }
        return result;
    }

    public  boolean searchCardById(int id)
    {
        for (Card c:cardsOwn) {
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
