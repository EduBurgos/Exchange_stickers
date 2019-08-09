package userSide;

import collection.Card;
import dao.UserDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that represents a user
 */
public class User {
    /**User's name*/
    private String nome;
    /**User's surname*/
    private String cognome;
    /**User's username*/
    private String username;
    /**User's password*/
    private String pass;
    /**User's email*/
    private String email;
    /**Cards owned by the user*/
    private ArrayList<Card> carte;

    public User(String nome, String cognome, String username, String email, ArrayList<Card> cartePossedute) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email=email;
        this.carte= cartePossedute;
    }



    /**
     * Overloading constructor used for new users
     * @param nome new user's name
     * @param cognome new user's surname
     * @param username new user's email
     * @param email new user's email
     */
    public User(String nome, String cognome, String username, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email=email;
        this.carte=null;
    }
    public User()
    {
        return;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Card> getCarte() {
        return carte;
    }
    public User finByUsername(String username) throws SQLException
    {
        UserDaoImpl userDao = new UserDaoImpl();
        return userDao.findByUsername(username);
    }

    public void setCarte(ArrayList<Card> carte) {
        this.carte = carte;
    }

    /**
     * Method used to see if there are two users with the same name, surname, username and email
     * @param second user that we want to check
     * @return true if there are two users with the same name, surname, username and email, false if not
     */

    public boolean equals(User second){ //metodo per definire se è uguale ad un altro user
        return (this.nome.equals(second.nome)) && (this.cognome.equals(second.cognome)) && (this.username.equals(second.username)) && (this.email.equals(second.email));
    }

    /**
     * Method that adds a card to the ones owned by the user
     * @param c card who is going to be added to the ones owned by the user
     * @return true if the card has been added, false if not
     */
    public boolean addCard(Card c){ ;
       return  this.carte.add(c);
    }

    /**
     * Override toString method
     * @return the cards owned by the user
     */
  /*public String toString(){
       String s= this.getUsername()+" Carte possedute:\n";
       for(Card c: carte) {
           s+= c.toString();
       }
       return s;
    }
*/


    /**
     * Method used to find a card by its name and type
     * @param nomec name of the card
     * @param tipo type of the card
     * @return card we are searching for, if it hasn't been found the method returns null
     */
    public Card findCard( String nomec, String tipo){
        for(Card c: carte){
            if(c.getNome().equals(nomec)&& c.getTipo().equals(tipo)){
                return c;
            }
        }
        System.err.println("Carta non trovata");
        return null;
    }




    /**
     * Method used to remove a card owned by the user
     * @param c card we want to remove
     */
    public void removeCard( Card  c ){

       carte.remove(c);
    }



    /**
     * Method used to see if a user has that particular card
     * @param card card we want to check
     * @return true if the card is owned by the user, false if not
     */
    public boolean checkHoldingCard(Card card){     //controllo tramite oggetto card
        return this.carte.contains(card);
    }

    /**
     * Method used to see if a user has that particular card by searching for its name
     * @param cardName name of the card we want to check
     * @return true if the card is owned by the user, false if not
     */

    public boolean checkHoldingCard(String cardName){      //controllo tramite nome della carta
        for (Card i: this.carte
             ) {
            if(i.getNome().equals(cardName)){
                return true;
            }
        }
        return false;
    }
    /**
     * Method used to see if a user has that particular card by searching for its id
     * @param id id of the card we want to check
     * @return true if the card is owned by the user, false if not
     */

    public boolean checkHoldingCard(int id){    //controllo tramite id della carta
        for (Card i : this.carte
             ) {
            if(i.getId()== id){
                return true;
            }
        }
        return false;
    }


}