package userSide;

import collection.Card;

import java.util.ArrayList;

public class Trattativa {

    private User offerer, receiver;
    private ArrayList<Card> Cardoffer;
    private ArrayList<Card> counterOffer;

    public Trattativa(User offerer, User receiver, ArrayList<Card> cardOffer, ArrayList<Card> counterOffer) {
        this.offerer = offerer;
        this.receiver = receiver;
        this.Cardoffer = cardOffer;
        this.counterOffer = counterOffer;
    }


    //metodo per trasformare una collection di card in una string di id divisa da dei trattini

    public String fromArraylistToString(ArrayList<Card> lista) {
        String idString = null;
        for (Card i : lista
        ) {
            idString.concat(String.valueOf(i.getId()));
            idString.concat("-");
        }
        //questo if rimuove l'ultimo trattino
        if (idString != null && idString.length() > 0 && idString.charAt(idString.length() - 1) == 'x') {
            idString = idString.substring(0, idString.length() - 1);
        }
        return idString;
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
        return Cardoffer;
    }

    public void setCardoffer(ArrayList<Card> cardoffer) {
        Cardoffer = cardoffer;
    }

    public ArrayList<Card> getCounterOffer() {
        return counterOffer;
    }

    public void setCounterOffer(ArrayList<Card> counterOffer) {
        this.counterOffer = counterOffer;
    }
}
