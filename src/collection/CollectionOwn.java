package collection;

import userSide.User;

import java.util.ArrayList;

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
}
