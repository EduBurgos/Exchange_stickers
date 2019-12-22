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
     * method of inserting a card
     * @param card
     * @return card
     */
    @Override
    public boolean insert(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.insert(card);
    }
    /**
     * method that finds a card by id
     * @param id
     * @return id
     */
    @Override
    public Card findByID(int id) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.findByID(id);
    }
    /**
     *method that modifies the object card
     * @param card
     * @return boolean
     */
    @Override
    public boolean update(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return  c.update(card);
    }
    /**
     *method that delete the object card
     * @param card
     * @return boolean
     */
    @Override
    public boolean delete(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.delete(card);
    }
    /**
     *method that finds a lot of cards
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
     *method that takes the owner's card
     * @param  user
     * @return Map<Card, Integer>
     */
    @Override
    public Map<Card, Integer> getCollentionOwn(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.getCollentionOwn(user);
    }
    /**
     *method that update the owner's card
     * @param
     * @return boolean
     */
    @Override
    public boolean update() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.update();
    }
    /**
     *method that delete the owner's card
     * @param
     * @return boolean
     */
    @Override
    public boolean delete() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.delete();
    }
    /**
     *method that create to random card
     * @param user
     * @return Card
     */
    @Override
    public Card createRandomCard(User user) throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.createRandomCard(user);
    }
    /**
     *method that gets the last card of the user on the DB.
     * @param user
     * @return Card
     */
    @Override
    public Card get_last_card(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.get_last_card(user);
    }
    /**
     *method used to generate a number of card for user
     * @param user
     * @return ArrayList<Card>
     */
    @Override
    public ArrayList<Card> openSachet(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.openSachet(user);
    }
    /**
     *method used to find a given card
     * @param (user, name, category, classCard, TypeCard)
     * @return ArrayList<Card>
     */
    @Override
    public ArrayList<Card> filters(User user, String name, String category, String classCard, String typeCard) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.filters(user, name, category, classCard, typeCard);
    }

    /*EXCHANGE CARD DAO*/
    /**
     *
     * @param
     * @return
     */
    @Override
    public void create(User user, Map<Integer, Integer> cardown, Map<Integer, Integer> cardwanted) throws SQLException {
        ExchangeCardDAO exchangeCardDAO=new ExchangeCardDAOImpl();
        exchangeCardDAO.create(user,cardown,cardwanted);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.marketExchange(exchangeCard);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public void delete(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        f.delete(id_trans);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getExchange(id_trans);

    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user, String Parameter) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getAllExchange(user, Parameter);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public ArrayList<Exchange> filtersexchange(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.filtersexchange(user, name, category, classCard, typeCard);
    }

    /*USER DAO*/
    /**
     *
     * @param
     * @return
     */
    @Override
    public boolean save(User user, String pass) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.save(user, pass);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findByUsername(username);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public boolean update(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.update(user);
    }
    /**
     *
     * @param
     * @return
     */
    @Override
    public boolean delete(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.delete(user);
    }
    /**
     *
     * @param
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
     * @return
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findAll();
    }
    /**
     *
     * @param
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
    public ArrayList<Exchange> exchangeToNotify(User user) throws SQLException{
        ExchangeCardDAO temp = new ExchangeCardDAOImpl();
        return temp.getAllExchange(user,"notify");
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
