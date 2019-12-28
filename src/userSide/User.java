package userSide;

import collection.Card;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Class that represents a user
 */
public class User {

    private String nome;
    private String cognome;
    private String username;
    private String pass;
    private String email;


    /**
     * Constructor for user
     * @param nome a String.Indicates user's name
     * @param cognome a String.Indicates user's surname
     * @param username a String.Indicates user's email
     * @param email a String.Indicates user's email
     */
    public User(String nome, String cognome, String username, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email=email;
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

    public String getPass() { return pass; }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





    /**
     * Checks if there are two users with the same name, surname, username and email
     * @param   second type User.Indicates a user who will be compared
     * @return  true if there are two users with the same name, surname, username and email, false if not
     */

    public boolean equals(User second){
        return (this.nome.equals(second.nome)) && (this.cognome.equals(second.cognome)) && (this.username.equals(second.username)) && (this.email.equals(second.email));
    }











    public String passToHash(String p) throws NoSuchAlgorithmException  {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(p.getBytes());
        byte[] digest = md.digest();
        //String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        //assertThat(myHash.equals(hash)).isTrue();
        System.out.println(digest.toString());
        return "asd";
    }


}