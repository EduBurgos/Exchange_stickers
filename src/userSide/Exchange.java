package userSide;

import java.util.ArrayList;

/** Class that represents the exchanges created by a user
 **/
public class Exchange {
    private int id_trans;
    private String username;
    private String username_offerente;
    private ArrayList<Integer> card_owm;
    private ArrayList<Integer> card_wanted;
    private boolean trans_comp; //true if completed else false

    /**
     * Constructor for uncompleted exchange
     * Used for users exchanges that are not accepted by someone , yet
     * @param id_trans an int number. Indicates the id on the database of the exchange
     * @param username a String. Indicates the username of the one who created the exchange
     * @param card_owm an ArrayList of integer. Indicates the ids of  cards to sell
     * @param card_wanted an ArrayList of integer. Indicates the ids of cards to receive
     */
    public Exchange(int id_trans,String username, ArrayList<Integer> card_owm, ArrayList<Integer> card_wanted) {
        this.id_trans = id_trans;
        this.username = username;
        this.card_owm = card_owm;
        this.card_wanted = card_wanted;
        this.username_offerente= null;
        this.trans_comp=false;
    }
    /**
     * Constructor for generic exchange
     * Used for users exchanges that are not accepted by someone , yet
     * @param id_trans an int number. Indicates the id on the database of the exchange
     * @param username a String. Indicates the username of the one who created the exchange
     * @param card_owm an ArrayList of integer. Indicates the ids of  cards to sell
     * @param card_wanted an ArrayList of integer. Indicates the ids of cards to receive
     * @param trans_comp  boolean.Indicates if the transaction is completed
     * @param username_offerente a String. Indicates the username of the buyer.
     */
    public Exchange(int id_trans, String username, ArrayList<Integer> card_owm, ArrayList<Integer> card_wanted,boolean trans_comp,String username_offerente) {
        this.id_trans = id_trans;
        this.username= username;
        this.card_owm = card_owm;
        this.card_wanted = card_wanted;
        this.trans_comp =trans_comp;
        this.username_offerente=username_offerente;
    }


    public int getId_trans() { return id_trans; }

    public void setId_trans(int id_trans) { this.id_trans = id_trans; }

    public String getId_user() { return username; }

    public void setId_user(String username) { this.username = username; }

    public ArrayList<Integer> get_id_card_owm() { return card_owm; }

    public void set_id_Card_owm(ArrayList<Integer> card_owm) { this.card_owm = card_owm; }

    public ArrayList<Integer> getId_card_wanted() { return card_wanted; }

    public void setId_card_wanted(ArrayList<Integer> card_wanted) { this.card_wanted = card_wanted; }

    public boolean isTrans_comp() { return trans_comp; }

    public void setTrans_comp(boolean trans_comp) { this.trans_comp = trans_comp;}
    public String getUsername_offerente() {return username_offerente;}
    public void setUsername_offerente(String username_offerente) {this.username_offerente=username_offerente;}
    public boolean get_trans_com() {
        return trans_comp;
     }
}
