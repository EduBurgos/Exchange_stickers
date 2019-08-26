package platform;//singleton pattern
// Java program implementing Singleton class
// with getInstance() method


import Server.MYSQLConnection;
import collection.Card;
import collection.CollectionOwn;
import dao.CollectionOwnDao;
import dao.CollectionOwnDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import userSide.User;

import java.io.IOException;

import java.sql.SQLException;
import java.util.*;

public class Platform {

    /** connection to MYSql*/
    private MYSQLConnection conn;
    private static Platform Platform_instance=null;



    private Platform() {
        this.conn= MYSQLConnection.getInstance();

    }

    public static Platform getInstance()
    {
        if(Platform_instance==null)
        {
            Platform_instance=new Platform();
        }
        return Platform_instance;
    }



    /**
     * This method controls if the user is already registered to the platform
     * @param username
     * @param pass
     * @return  CollectionOwn logged
     * @exception SQLException
     */
    //metodo per il login
    public CollectionOwn LogIn(String username,String pass) throws SQLException {
        User logg = null;

        try {
            UserDao userDao = new UserDaoImpl();
            logg = userDao.findByUsername(username);
            System.out.println("Stampo logg "+logg);

            String a = logg.getPass();
            System.out.println("Stampo a "+a);

            if(a.equals(pass))
            {
                System.out.println("Sono dentro il if controllo pass platform");
                //quando loggo carico anche carte utente in collectionOwn
                CollectionOwnDaoImpl collectionOwnDao=new CollectionOwnDaoImpl();
                CollectionOwn collectionOwn=new CollectionOwn(logg,collectionOwnDao.getCollentionOwn(logg));
                return collectionOwn;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method used to sign up a new user
     * @param name of new user
     * @param lastName of new user
     * @param username of new user
     * @param email of new user
     * @param password of new user
     * @return  true if the user is registered false if the registration fails
     * @throws SQLException
     */
    public boolean SignUp(String name, String lastName, String username, String email, String password,String retype) throws SQLException {
          User reg = new User(name, lastName, username, email);
          UserDaoImpl userTemp = new UserDaoImpl();
          try{
            if (userTemp.checkUnique(reg) && password.equals(retype)) {
                boolean succ = userTemp.save(reg, password);         /**User salvato correttamente*/
                return succ;
            }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            return false;
    }

    //metodo controllo usernames
    public Boolean checkUsername(User user){

    /*    for (User i: this.users
        ) {
            if(i.getUsername().equals(user.getUsername())){
                return false;}
        }*/
        return true;
    }


    //mmh inutile per ora
    public Boolean checkUsername(String user){

       /* for (User i: this.users
        ) {
            if(i.getUsername().equals(user)){
                return false;}
        }*/
        return true;
    }
    //cerca un utente dal suo username
    public User findUser(String user)
    {
        /*for (User i: this.users
        ) {
            if(i.getUsername().equals(user)){
                return i;}
        }*/
        return null;
    }


    /**
     * Method that checks if the mail is valid
     * @param email of the user who is signing up to the platform
     * @return true if the mail is valid false otherwise
     */
    private Boolean checkEmail(String email)
    {
        return email.matches("[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}");
    }


    //metodo per aggiungere carta scegliendo da Catalogo tramite posizione
    //perchè string user? non è utente loggato?
    public void addCard(String user, int indice) {
      /*  User s;
        s = findUser(user);
        try {
            if (s != null) {
                s.addCard(cat.getCard(indice));
            }
            else //la condizione è già rispettata perchè utente loggato!
                System.err.println("Utente non trovato");
        } catch (IndexOutOfBoundsException io){
            System.err.println("Carta non trovata nel catalogo.\nImpossibile aggiungere carta.");
        }*/
    }
    public CollectionOwn searcIntoCollection(CollectionOwn collectionOwn,String toSearch)
    {
        return new CollectionOwn(collectionOwn.getOwner(),collectionOwn.searchCard(toSearch));
    }

    // metodo per rimuovere carta dell'utente
    public void removeCard(User user, Card card){

        UserDao ud = new UserDaoImpl();


       /* User s;
        s=findUser(user);
        if(s!=null) {
            s.removeCard(s.findCard(nomec, tipo));
        }
        else
            System.err.println("utente non trovato");*/
    }
    /*public boolean insertIntoCollection(CollectionOwn collectionOwn,Card card,int quantity) throws SQLException
    {
        return collectionOwn.insert(card,quantity);
    }*/



}
