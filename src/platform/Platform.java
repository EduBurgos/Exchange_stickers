package platform;//singleton pattern
// Java program implementing Singleton class
// with getInstance() method


import Server.MYSQLConnection;
import collection.Card;
import collection.CollectionOwn;
import dao.*;
import userSide.Exchange;
import userSide.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import java.io.IOException;

import java.sql.SQLException;
import java.util.*;

public class Platform {

    /** connection to MYSql*/
    private MYSQLConnection conn;
    private static Platform Platform_instance=null;

    private Platform() { this.conn= MYSQLConnection.getInstance(); }

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
            String a = logg.getPass();
            if(a.equals(pass))
            {
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
            if (userTemp.checkUnique(reg) && checkEmail(email)) {
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
    //TODO sistemare commento con parametri giusti
    /**
     * Method used to insert an exchange record
     * @return  true if the exchange is registered false if the registration fails
     * @throws SQLException
     */
    public void setExchange(int idTrans, String Username, int[] idCardOwn, int[] idCardWanted) throws SQLException {
        Exchange exchange = new Exchange(idTrans, Username, idCardOwn, idCardWanted);
        ExchangeCardDAOImpl exchangeCardDAO = new ExchangeCardDAOImpl();
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User user = userDaoImpl.findByUsername(Username);
        ArrayList<Card> ownedCards = new ArrayList<Card>();
        ArrayList<Card> wantedCards = new ArrayList<Card>();

        CardsDaoImpl cardsDaoImpl = new CardsDaoImpl();
        for(int i=0; i<idCardOwn.length; i++){
            ownedCards.add(cardsDaoImpl.findByID(idCardOwn[i]));
        }
        for(int i=0; i<idCardWanted.length; i++){
            ownedCards.add(cardsDaoImpl.findByID(idCardWanted[i]));
        }
        exchangeCardDAO.create(user, ownedCards, wantedCards);
    }

    /**
     * Method used to accept an exchange
     * @throws SQLException
     */
    public boolean acceptExchange() {
        //controllo se carte richieste ci sono
        //controllo se carte volute sono in possesso di chi a settato l'offerta
        //try catch di un metodo private che svolge i settaggi delle nuova carte, in modo da bloccare entrambe le transazioni qualora qualcosa non andasse bene
        //if che restituisce true se ha passato i controlli e ha fatto l'update su db
        return false;
    }


    /**Metodo curiosita carte di altri user*/
    public CollectionOwn SnitchCards(String username) throws SQLException{
        User nick = null;
        try {
            UserDao userDao = new UserDaoImpl();
            nick = userDao.findByUsername(username);

            if(nick != null)
            {
                //quando loggo carico anche carte utente in collectionOwn
                CollectionOwnDaoImpl collectionOwnDao=new CollectionOwnDaoImpl();
                CollectionOwn collectionOwn = new CollectionOwn(nick, collectionOwnDao.getCollentionOwn(nick));
                return collectionOwn;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Metodo che prende tutte le trattative*/
    public ArrayList<Exchange> getExchange() throws SQLException{
        try{
            ExchangeCardDAOImpl market = new ExchangeCardDAOImpl();
            for (Exchange x : market.getAllExchange()
                 ) {
                System.out.println("inizio stampa exchange: ");
                System.out.println(x.getId_user());
                System.out.println(x.getId_trans());
                System.out.println(x.getId_card_owm());
                System.out.println(x.getId_card_wanted());
                System.out.println(x.isTrans_comp());
            }
            return market.getAllExchange();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    /**METODO PASSWORD*/

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
