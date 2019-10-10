package userSide;

import collection.Card;

import java.util.ArrayList;

public class Exchange {
    private int id_trans;
    private String username;
    private String username_offerente;
    private int[] id_card_owm;
    private int[] id_card_wanted;
    private boolean trans_comp; //true se completata altrimenti false

    public Exchange(String username, int[] id_card_owm, int[] id_card_wanted) {
        this.id_trans = id_trans;
        this.username = username;
        this.id_card_owm = id_card_owm;
        this.id_card_wanted = id_card_wanted;
        this.username_offerente= null;
        this.trans_comp=false;
    }
    public Exchange(int id_trans, String username, int[] id_card_owm, int[] id_card_wanted, boolean trans_comp,String username_offerente) {
        this.id_trans = id_trans;
        this.username= username;
        this.id_card_owm = id_card_owm;
        this.id_card_wanted = id_card_wanted;
        this.trans_comp = trans_comp;
        this.username_offerente=username_offerente;
    }


    public int getId_trans() { return id_trans; }

    public void setId_trans(int id_trans) { this.id_trans = id_trans; }

    public String getId_user() { return username; }

    public void setId_user(String username) { this.username = username; }

    public int[] getId_card_owm() { return id_card_owm; }

    public void setId_card_owm(int[] id_card_owm) { this.id_card_owm = id_card_owm; }

    public int[] getId_card_wanted() { return id_card_wanted; }

    public void setId_card_wanted(int[] id_card_wanted) { this.id_card_wanted = id_card_wanted; }

    public boolean isTrans_comp() { return trans_comp; }

    public void setTrans_comp(boolean trans_comp) { this.trans_comp = trans_comp;}
    public String getUsername_offerente() {return username_offerente;}
    public void setUsername_offerente(String username_offerente) {this.username_offerente=username_offerente;}

}
