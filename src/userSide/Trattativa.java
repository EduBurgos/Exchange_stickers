package userSide;

import collection.Card;

import java.util.ArrayList;

public class Trattativa {

    private User offerer, receiver;
    private ArrayList<Card> cardoffer;
    private ArrayList<Card> counterOffer;
    private boolean ended; //false se in corso , true se è conclusa

    public Trattativa(User offerer, User receiver, ArrayList<Card> cardOffer, ArrayList<Card> counterOffer) {
        this.offerer = offerer;
        this.receiver = receiver;
        this.cardoffer = cardOffer;
        this.counterOffer = counterOffer;
        this.ended=false;
    }
    public Trattativa(User offerer, ArrayList<Card> cardOffer, ArrayList<Card> counterOffer) {
        this.offerer = offerer;
        this.receiver = null;
        this.cardoffer = cardOffer;
        this.counterOffer = counterOffer;
        this.ended=true;
    }


    public User getOfferer() {
        return offerer;
    }

    public void setOfferer(User offerer) {
        this.offerer = offerer;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public ArrayList<Card> getCardoffer() {
        return cardoffer;
    }

    public void setCardoffer(ArrayList<Card> cardoffer) {
        cardoffer = cardoffer;
    }

    public ArrayList<Card> getCounterOffer() {
        return counterOffer;
    }

    public void setCounterOffer(ArrayList<Card> counterOffer) {
        this.counterOffer = counterOffer;
    }

    public boolean getStatus() {
        return ended;
    }

    public void setStatus(boolean ended) {
        this.ended = ended;
    }
}
