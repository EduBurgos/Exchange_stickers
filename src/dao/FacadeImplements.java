package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FacadeImplements implements Facade
{
    /*CARDSDAO IMPLEMENT*/
    /**
     * method of inserting a card
     * @param card
     * @return return boolean, verification of successful insertion.
     */
    @Override
    public boolean insert(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.insert(card);
    }
    /**
     * method that finds a card by id
     * @param id
     * @return returns a card given an id
     */
    @Override
    public Card findByID(int id) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.findByID(id);
    }

    @Override
    public ArrayList<Card> filterCatalog(String nameCard, String category, String classCard, String typeCard) {
        CardsDaoImpl c=new CardsDaoImpl();
        return c.filterCatalog(nameCard,category,classCard,typeCard);
    }

    /**
     * method that modifies the object card
     * @param card
     * @return boolean, check if the change was successful
     */
    @Override
    public boolean update(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return  c.update(card);
    }
    /**
     * method that delete the object card
     * @param card
     * @return boolean
     */
    @Override
    public boolean delete(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.delete(card);
    }
    /**
     * method that finds a lot of cards
     * @param
     * @return ArrayList<Card>
     */
    @Override
    public ArrayList<Card> findAllGeneric() throws SQLException {
        CardsDaoImpl f = new CardsDaoImpl();
        return f.findAllGeneric();
    }

    /*COLLECTION ONW DAO*/
    /**
     * method that takes the owner's card
     * @param  user
     * @return get collection Own
     */
    @Override
    public Map<Card, Integer> getCollentionOwn(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.getCollentionOwn(user);
    }
    /**
     * method that update the owner's card
     * @param
     * @return boolean, update the owner's card
     */
    @Override
    public boolean update() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.update();
    }
    /**
     * method that delete the owner's card
     * @param
     * @return boolean, delete the owner's card
     */
    @Override
    public boolean delete() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.delete();
    }
    /**
     * method that create to random card
     * @param user
     * @return Card, create to random card
     */
    @Override
    public Card createRandomCard(User user) throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.createRandomCard(user);
    }
    /**
     * method that gets the last card of the user on the DB.
     * @param user
     * @return Card
     */
    @Override
    public Card get_last_card(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.get_last_card(user);
    }
    /**
     * method used to generate a number of card for user
     * @param user
     * @return ArrayList<Card>
     */
    @Override
    public ArrayList<Card> openSachet(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.openSachet(user);
    }
    /**
     * Finds certain cards in user's collection through applied filters
     * @param user type User. Indicates the owner of the collection
     * @param name type String. Indicates name of the card searched
     * @param category type String. Indicates category of the cards searched
     * @param classCard type String Indicates class of the cards searched
     * @param typeCard type String. Indicates type of the cards searched
     * @return ArrayList<Card> that contains cards found through filters
     */
    @Override
    public ArrayList<Card> filters(User user, String name, String category, String classCard, String typeCard) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.filters(user, name, category, classCard, typeCard);
    }

    /**
     * Gifts new cards to the user who did daily access. Cards can be gifted max once a day.
     * @param user type User. Indicates user who receives new cards
     */
    @Override
    public void giftCard(User user){
        CollectionOwnDao f = new CollectionOwnDaoImpl();
        f.giftCard(user);
    }

    /*EXCHANGE CARD DAO*/
    /**
     * TODO: Capire cosa fa questo metodo
     * @param user
     * @param cardown
     * @param cardwanted
     * @return Capire cosa fa questo metodo
     */
    @Override
    public int create(User user, Map<Integer, Integer> cardown, Map<Integer, Integer> cardwanted) throws SQLException {
        ExchangeCardDAO exchangeCardDAO=new ExchangeCardDAOImpl();
        return exchangeCardDAO.create(user,cardown,cardwanted);
    }
    /**
     *TODO: Capire cosa fa questo metodo
     * @param exchangeCard
     * @return boolean
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.marketExchange(exchangeCard);
    }
    /**
     * method that cancels a transaction
     * @param id_trans
     * @return cancels a transaction
     */
    @Override
    public void delete(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        f.delete(id_trans);
    }
    /**
     * Through id a transaction is found
     * @param id_trans a String. Indicates id of the transaction that has to be found
     * @return Exchange searched
     */
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getExchange(id_trans);

    }
    /**
     * method that returns all transactions
     * @param user
     * @param Parameter
     * @return  ArrayList<Exchange>, returns all transactions
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user, String Parameter) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getAllExchange(user, Parameter);
    }
    /**
     * Filters the exchanges given certain parameters except for the ones started by a certain user
     * @param user type User. Indicates user whose exchanges don't have to be found
     * @param name  a String. Indicates name of the card offered belonging to searched exchange.
     * @param category  a String. Indicates category of the cards offered belonging to searched exchange.
     * @param classCard a String. Indicates  class of the cards offered belonging to searched exchange.
     * @param typeCard  a String. Indicates type  of the cards offered that belonging to searched exchange.
     * @return ArrayList<Exchange> that contains all the exchanges found
     */
    @Override
    public ArrayList<Exchange> filtersExchange(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.filtersExchange(user, name, category, classCard, typeCard);
    }

    /*USER DAO*/
    /**
     * Saves a new user
     * @param user type User. Indicates user who is going to be saved
     * @param pass a String. Indicates password of the new user
     * @return boolean if new user is saved false otherwise
     */
    @Override
    public boolean save(User user, String pass) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.save(user, pass);
    }
    /**
     * Finds a user by their username
     * @param username a String. Indicates user's username
     * @return User found
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findByUsername(username);
    }
    /**
     * method that update to user
     * @param user
     * @return boolean, update to user
     */
    @Override
    public boolean update(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.update(user);
    }
    /**
     * method that delete to user
     * @param user
     * @return boolean, delete to user
     */
    @Override
    public boolean delete(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.delete(user);
    }
    /**
     * checks if a user exists in the DB
     * @param user
     * @return boolean
     */
    @Override
    public boolean checkByUser(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkByUser(user);
    }
    /**
     * method that find all user
     * @param
     * @return ArrayList<User>, find all user
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findAll();
    }
    /**
     * method used to verify that a user does not exist in the database, user in registration.
     * @param user
     * @return boolean, verify that a user does not exist in the database
     */
    @Override
    public boolean checkUnique(User user) {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkUnique(user);
    }
    /**
     * shows notifications when a transaction has been accepted
     * @param exchange
     * @return shows exchange notifications
     */
    @Override
    public void setExchangeNotified(Exchange exchange) throws SQLException {
        ExchangeCardDAO temp = new ExchangeCardDAOImpl();
        temp.setExchangeNotified(exchange);
    }
}
