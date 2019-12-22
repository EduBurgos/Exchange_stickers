package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FacadeImplements implements Facade
{
    /*CARDDAO IMPLEMENT*/
    /**
     *
     * @param card
     * @return method of inserting a card
     */
    @Override
    public boolean insert(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.insert(card);
    }
    /**
     *
     * @param id
     * @return method that finds a card by id
     */
    @Override
    public Card findByID(int id) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.findByID(id);
    }
    /**
     *
     * @param card
     * @return method that modifies the object card
     */
    @Override
    public boolean update(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return  c.update(card);
    }
    /**
     *
     * @param card
     * @return method that delete the object card
     */
    @Override
    public boolean delete(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.delete(card);
    }
    /**
     *
     * @param
     * @return method that finds a lot of cards
     */
    @Override
    public ArrayList<Card> findAllGeneric() throws SQLException {
        CardsDaoImpl f = new CardsDaoImpl();
        return f.findAllGeneric();
    }

    /*COLLECTION ONW DAO*/
    /**
     *
     * @param  user
     * @return method that takes the owner's card
     */
    @Override
    public Map<Card, Integer> getCollentionOwn(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.getCollentionOwn(user);
    }
    /**
     *
     * @param
     * @return method that update the owner's card
     */
    @Override
    public boolean update() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.update();
    }
    /**
     *
     * @param
     * @return method that delete the owner's card
     */
    @Override
    public boolean delete() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.delete();
    }
    /**
     *
     * @param user
     * @return method that create to random card
     */
    @Override
    public Card createRandomCard(User user) throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.createRandomCard(user);
    }
    /**
     *
     * @param user
     * @return method that gets the last card of the user on the DB.
     */
    @Override
    public Card get_last_card(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.get_last_card(user);
    }
    /**
     *
     * @param user
     * @return method used to generate a number of card for user
     */
    @Override
    public ArrayList<Card> openSachet(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.openSachet(user);
    }
    /**
     *
     * @param user
     * @param name
     * @param category
     * @param classCard
     * @param typeCard
     * @return method used to find a given card
     */
    @Override
    public ArrayList<Card> filters(User user, String name, String category, String classCard, String typeCard) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.filters(user, name, category, classCard, typeCard);
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
    public void create(User user, Map<Integer, Integer> cardown, Map<Integer, Integer> cardwanted) throws SQLException {
        ExchangeCardDAO exchangeCardDAO=new ExchangeCardDAOImpl();
        exchangeCardDAO.create(user,cardown,cardwanted);
    }
    /**
     *TODO: Capire cosa fa questo metodo
     * @param exchangeCard
     * @return
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.marketExchange(exchangeCard);
    }
    /**
     *
     * @param id_trans
     * @return method that cancels a transaction
     */
    @Override
    public void delete(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        f.delete(id_trans);
    }
    /**
     *TODO: Capire cosa fa questo metodo
     * @param id_trans
     * @return Capire cosa fa questo metodo
     */
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getExchange(id_trans);

    }
    /**
     *
     * @param user
     * @param Parameter
     * @return method that returns all transactions
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user, String Parameter) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getAllExchange(user, Parameter);
    }
    /**
     *
     * @param user
     * @param name
     * @param category
     * @param classCard
     * @param typeCard
     * @return method that filters the exchanges given certain parameters
     */
    @Override
    public ArrayList<Exchange> filtersexchange(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.filtersexchange(user, name, category, classCard, typeCard);
    }

    /*USER DAO*/
    /**
     *
     * @param user
     * @param pass
     * @return method that save a new user
     */
    @Override
    public boolean save(User user, String pass) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.save(user, pass);
    }
    /**
     *
     * @param username
     * @return method that find a user
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findByUsername(username);
    }
    /**
     *
     * @param user
     * @return method that update to user
     */
    @Override
    public boolean update(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.update(user);
    }
    /**
     *
     * @param user
     * @return method that delete to user
     */
    @Override
    public boolean delete(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.delete(user);
    }
    /**
     * TODO: Capire cosa fa questo metodo
     * @param user
     * @return
     */
    @Override
    public boolean checkByUser(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkByUser(user);
    }
    /**
     *
     * @param
     * @return method that find all user
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findAll();
    }
    /**
     *TODO: Capire cosa fa questo metodo
     * @param user
     * @return
     */
    @Override
    public boolean checkUnique(User user) {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkUnique(user);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public void setExchangeNotified(Exchange exchange) throws SQLException {
        ExchangeCardDAO temp = new ExchangeCardDAOImpl();
        temp.setExchangeNotified(exchange);
    }
}
