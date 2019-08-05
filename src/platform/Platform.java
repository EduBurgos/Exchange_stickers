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
import java.util.ArrayList;
import java.util.Random;

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
     * @return  user logged
     * @exception SQLException
     */
    //metodo per il login
    public User LogIn(String username,String pass) throws SQLException {
        User logg = null;
        try {
            UserDaoImpl userDao = new UserDaoImpl();
            logg = userDao.findByUsername(username);
            String a = logg.getPass();
            if(a.equals(pass))
            {
                //quando loggo carico anche carte utente in collectionOwn
                CollectionOwnDaoImpl co=new CollectionOwnDaoImpl();
                CollectionOwn collectionOwn;
                collectionOwn = new CollectionOwn(logg,co.create_collection(logg));
                return logg;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Method that generates the password when the user signs up to the platform
     * @return user's password
     */

    public String generaPass()
    {
        Random rnd= new Random();
        char[] arr=new char[7];
        for (int i=0;i<7;i++)
        {
            int n=rnd.nextInt(21);
            arr[i]=(char)(n<10 ? '0'+7:'a'+n-10);
        }
        return new String(arr);
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
    public User signUp()
    {
        return null;
    }


}
