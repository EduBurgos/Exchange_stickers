package collection;

import userSide.User;

import java.util.ArrayList;
import java.util.Map;

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
    public void setCardsOwn(Map<Card,Integer> cardsOwn) {
        this.cardsOwn = cardsOwn;
    }

}
