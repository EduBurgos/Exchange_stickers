package userSide;

import collection.Card;

import java.util.ArrayList;

public class Exchange {
    private int id_trans;
    private int id_user;
    private int id_user_offerta;
    private int[] id_card_owm;
    private int[] id_card_wanted;
    private boolean trans_comp; //true se completata altrimenti false

    public Exchange(int id_trans, int id_user, int[] id_card_owm, int[] id_card_wanted, boolean trans_comp) {
        this.id_trans = id_trans;
        this.id_user = id_user;
        this.id_card_owm = id_card_owm;
        this.id_card_wanted = id_card_wanted;
        this.id_user_offerta=null;
        this.trans_comp=false;
    }
    public Exchange(int id_trans, int id_user, int[] id_card_owm, int[] id_card_wanted, boolean trans_comp,int id_user_offerta) {
        this.id_trans = id_trans;
        this.id_user = id_user;
        this.id_card_owm = id_card_owm;
        this.id_card_wanted = id_card_wanted;
        this.trans_comp = trans_comp;
        this.id_user_offerta=id_user_offerta;
    }


    public int getId_trans() { return id_trans; }

    public void setId_trans(int id_trans) { this.id_trans = id_trans; }

    public int getId_user() { return id_user; }

    public void setId_user(int id_user) { this.id_user = id_user; }

    public int[] getId_card_owm() { return id_card_owm; }

    public void setId_card_owm(int[] id_card_owm) { this.id_card_owm = id_card_owm; }

    public int[] getId_card_wanted() { return id_card_wanted; }

    public void setId_card_wanted(int[] id_card_wanted) { this.id_card_wanted = id_card_wanted; }

    public boolean isTrans_comp() { return trans_comp; }

    public void setTrans_comp(boolean trans_comp) { this.trans_comp = trans_comp;}
    public int getId_user_offerta() {return id_user_offerta;}
    public void setId_user_offerta(int id_user_offerta) {this.id_user_offerta=id_user_offerta;}

}
