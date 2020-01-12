package userSide;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that represents a user
 */
public class User {

    private String nome;
    private String cognome;
    private String username;
    private String pass;
    private String email;


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
     * @param   second  user who will be compared
     * @return  true if there are two users with the same name, surname, username and email, false otherwise
     */

    public boolean equals(User second){
        return (this.nome.equals(second.nome)) && (this.cognome.equals(second.cognome)) && (this.username.equals(second.username)) && (this.email.equals(second.email));
    }




}