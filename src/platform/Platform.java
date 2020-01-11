package platform;

import collection.Card;
import collection.CollectionOwn;
import dao.*;
import userSide.Exchange;
import userSide.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.sql.SQLException;
import java.util.*;

/**
 * Class that represents platform where users can create exchanges and exchange their cards
 */
public class Platform {

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn;
    private static Platform Platform_instance=null;

    private Platform() {
        this.conn=connector.createConnection();
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
     * Returns the collection of the logged users
     * This method checks if the user is already registered to the platform and if username and password are correct
     * @param username a String. Username of the user to check
     * @param pass a String. User's password
     * @return  CollectionOwn collection of the user who successfully logged
     * @exception SQLException exception caused by database access error
     */
    public CollectionOwn LogIn(String username,String pass) throws SQLException {
        User logg = null;

        try {

            Facade f = new FacadeImplements();

            logg = f.findByUsername(username);
            String a = logg.getPass();
            if(a.equals(pass))
            {
                //quando loggo carico anche carte utente in collectionOwn
                return f.getCollentionOwn(logg);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method used to sign up a new user
     * @param name a String. Indicates name of new user
     * @param lastName a String. Indicates surname of new user
     * @param username a String. Indicates username of new user
     * @param email a String. Indicates email of new user
     * @param password a String. Indicates password of new user
     * @return  true if the user is registered false if the registration fails
     * @throws SQLException exception caused by database access error
     */
    public boolean signUp(String name, String lastName, String username, String email, String password, String retype) throws SQLException {
          User reg = new User(name, lastName, username, email);
          FacadeImplements userTemp = new FacadeImplements();
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

    /**
     * Finds a user by their id
     * @param id a int. Indicates id of the searched user
     * @return User user searched
     * */
    public User findUser(String id) throws SQLException
    {
        User account=null;
        try{
            Facade user = new FacadeImplements();
            account= user.findByUsername(id);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Checks if the mail is valid
     * @param email a String. Indicates the mail of the user who is signing up to the platform
     * @return true if the mail is valid false otherwise
     */
    private Boolean checkEmail(String email)
    {
        return email.matches("[A-z0-9\\.\\+_-]+@[A-z0-9\\._-]+\\.[A-z]{2,6}");
    }

    public CollectionOwn searcIntoCollection(CollectionOwn collectionOwn,String toSearch)
    {
        return new CollectionOwn(collectionOwn.getOwner(),collectionOwn.searchCard(toSearch));
    }

    /**
     * Method used to insert an exchange record
     * @param Username a String. Indicates username of the user who creates a new exchange
     * @param CardOwn a ArrayList<Integer>. Contains the ids of all the cards the user want to offer
     * @param CardWanted a ArrayList<Integer>. Contains the ids of  all the cards the user want to have
     * @return  true if the exchange is registered false if the registration fails
     * @throws SQLException exception caused by database access error
     */
    public int setExchange(String Username, ArrayList<Integer> CardOwn, ArrayList<Integer> CardWanted) throws SQLException {
        FacadeImplements f = new FacadeImplements();
        User user = f.findByUsername(Username);
        Map<Integer,Integer> ownedCards = new HashMap<>();
        Map<Integer,Integer> wantedCards = new HashMap<>();

        for (int c: CardOwn) {
            if(ownedCards.containsKey(c))
            {
                int app=ownedCards.get(c)+1;
                ownedCards.replace(c,app);
            }
            else
            {
                ownedCards.put(c,1);
            }
        }

        for (int c: CardWanted)
        {
            if(wantedCards.containsKey(c))
            {
                int app=wantedCards.get(c)+1;
                wantedCards.replace(c,app);
            }
            else
            {
                wantedCards.put(c,1);
            }
        }
        return f.create(user,ownedCards,wantedCards);
    }

    /**
     * Accepts exchange created by another user
     * @param exchange type Exchange. Indicates exchange that a user wants to accept
     * @param loggato a String. Indicates username of the user who accepts the exchange
     * @return true if the exchange is successful, false otherwise
     */
    public boolean marketExchange(Exchange exchange,String loggato) {
        Facade f = new FacadeImplements();
        exchange.setUsername_offerente(loggato);
        return f.marketExchange(exchange);
    }

    /**
     * Deletes a transaction by its id
     * @param id_trans a int. Indicates id that identifies transaction
     * @throws SQLException exception caused by database access error
     */
    public void delete(int id_trans) throws SQLException{
        Facade f=new FacadeImplements();
        f.delete(id_trans);
    }

    /**
     * Shows cards that belong to the collection of the user searched
     * @param username a String. Indicates username of user searched
     * @return CollectionOwn all cards that belong to the user
     */
    public CollectionOwn SnitchCards(String username) throws SQLException{
        User nick = null;
        try {
            Facade u = new FacadeImplements();
            nick = u.findByUsername(username);

            if(nick != null) {
                //quando loggo carico anche carte utente in collectionOwn
                FacadeImplements f = new FacadeImplements();
                CollectionOwn collectionOwn = f.getCollentionOwn(nick);
                return collectionOwn;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Filters cards belonging to logged user  by the name, category,
     * class and/or type
     * @param username a String.Indicates username of the user logged
     * @param nameCard a String.Indicates name of the card searched
     * @param category a String.Indicates category of the cards searched
     * @param classCard a String.Indicates class of the cards searched
     * @param typeCard a String.Indicates type of the cards searched
     * @return ArrayList<Card> Indicates all cards filtered
     * @throws SQLException exception caused by database
     */
    public ArrayList<Card> filtersCollections(String username,String nameCard,String category, String classCard, String typeCard) throws SQLException{
        ArrayList<Card> list= new ArrayList<Card>();
        User account;
        try{
            Facade user = new FacadeImplements();
            account= user.findByUsername(username);
            if(account!=null){
                FacadeImplements f = new FacadeImplements();
                list = f.filters(account,nameCard,category,classCard,typeCard);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     *Filters exchanges by the name, category, class and/or type of one of the cards
     * offered in the exchange made by other users except logged user
     * @param username a String.Indicates username of logged user
     * @param nameCard a String.Indicates name of one of the cards searched offered in exchange
     * @param category a String.Indicates category of one of the cards searched offered in exchange
     * @param classCard a String.Indicates class of one of the cards searched offered in exchange
     * @param typeCard a String.Indicates type of one of the cards searched offered in exchange
     * @return ArrayList<Exchange> Indicates all the exchanges filtered
     * @throws SQLException exception caused by database
     */
    public ArrayList<Exchange> filtersExchanges(String username,String nameCard,String category, String classCard, String typeCard) throws SQLException{
        ArrayList<Exchange> list= new ArrayList<>();
        User account=null;
        try{
            Facade user = new FacadeImplements();
            account= user.findByUsername(username);
            if(account!=null){
                FacadeImplements f = new FacadeImplements();
                list = f.filtersExchange(account,nameCard,category,classCard,typeCard);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Filters cards of the catalog by the name, category,
     * class and/or type
     * @param nameCard a String.Indicates name of the card searched
     * @param category a String.Indicates category of the cards searched
     * @param classCard a String.Indicates class of the cards searched
     * @param typeCard a String.Indicates type of the cards searched
     * @return ArrayList<Card> Indicates all cards filtered
     */
    public ArrayList<Card>filterCatalog(String nameCard,String category,String classCard, String typeCard){
        ArrayList<Card> list= new ArrayList<Card>();

        try{
            Facade f = new FacadeImplements();
            list = f.filterCatalog(nameCard,category,classCard,typeCard);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }


    /**
     * Gives new cards when a user does their daily access
     * @param user type User. Indicates user tht has to be checked to see if it's their first time they logged to the
     * platform today  and they have to receive their daily cards
     * @throws SQLException Exception caused by database
     */
    public void giftCard(User user) throws SQLException{
        User account;
        try{
            Facade u = new FacadeImplements();
            account=u.findByUsername(user.getUsername());
            if(account!=null){
                FacadeImplements f = new FacadeImplements();
                f.giftCard(user);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


 /**
  * Shows all the exchanges the logged user can accept
  * @param user type User. Indicates user who logged to the platform
  * @return ArrayList<Exchange> all the available exchanges created by other users
  * */
  public ArrayList<Exchange> getAllExchanges(User user) throws SQLException{
      try {
          Facade f = new FacadeImplements();
          return  f.getAllExchange(user,"all");
      }catch(NullPointerException e){
          e.printStackTrace();
      }
      return null;
  }

    /**
     * Shows all my exchanges
     * @param user type User. Indicates user who is the owner of the exchanges
     * @return ArrayList<Exchange> get all my exchanges
     * */
  public ArrayList<Exchange> getAllMyExchnages(User user)  throws SQLException{
      try {
          Facade f = new FacadeImplements();
          return f.getAllExchange(user,"mine");
      }
      catch (Error e)
      {
          e.printStackTrace();
      }
      return null;
  }

    /**METODO PASSWORD*/
    private static SecretKeySpec secretKey;
    private static byte[] key;

    /**
     * Digest myKey using a hashing algorithm
     * @param myKey
     * */
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

    /**
     * Method that take strToEncrypt using as seed secret*/
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

    /**Metodo decryp*/
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

    /**
     * Finds all the cards in the catalog
     * @return ArrayList<Card> all cards that belong to the catalog
     * */
    public ArrayList<Card> allCardsCatalog () throws SQLException {
        FacadeImplements allCards = new FacadeImplements();
        return allCards.findAllGeneric();
    }

    /**
     * Method that find exchanges not yet notified to the user
     * @param user type User. Indicates user who  has to be notified
     * @return ArrayList<Exchange> Exchanges ought to be notified
     * @throws SQLException
     */
    public ArrayList<Exchange> notifyDoneExchanges(User user) throws SQLException{
        FacadeImplements temp = new FacadeImplements();
        ArrayList<Exchange> result = temp.getAllExchange(user,"notify");
        for (Exchange ex: result
             ) {
            temp.setExchangeNotified(ex);
        }
        return result;
    }

    /**
     * Finds a card by its id in the catalog
     * @param id a int. Indicates id of the card searched
     * @return Card searched
     * */
    public Card findCardByID(int id) throws SQLException {
        FacadeImplements temp = new FacadeImplements();
        return temp.findByID(id);
    }

    /**
     * Find an exchange by its id
     * @param idExchange int. Indicates id of the exchange searched
     * @return Exchange that is searched
     * @throws SQLException exception caused by database access error
     */
    public Exchange getExchange(int idExchange) throws SQLException {
        FacadeImplements temp = new FacadeImplements();
        return temp.getExchange(idExchange);
    }

    private final int numbercard = 6;

    /**
     * Gives cards to the user who just signed up to the platform
     * @param name a String. Indicates name of the user
     * @param lastName a String. Indicates surname of the user
     * @param username a String. Indicates username of the user
     * @param email a String. Indicates email of the user
     * @throws SQLException exception caused by database access error
     */
    public void userStartUpCards(String name, String lastName, String username, String email) throws SQLException{
        User u = new User(name, lastName, username, email);
        Facade card = new FacadeImplements();
        for(int i = 0; i<numbercard; i++) {
            card.createRandomCard(u);
        }
    }
}
