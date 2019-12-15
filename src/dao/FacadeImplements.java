package dao;

import collection.Card;
import userSide.Exchange;
import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FacadeImplements implements Facade
{
    @Override
    public boolean insert(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.insert(card);
    }
    @Override
    public Card findByID(int id) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.findByID(id);
    }
    @Override
    public boolean update(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return  c.update(card);
    }
    @Override
    public boolean delete(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.delete(card);
    }

    @Override
    public ArrayList<Card> findAllGeneric() throws SQLException {
        CardsDaoImpl f = new CardsDaoImpl();
        return f.findAllGeneric();
    }

    /*COLLECTION ONW DAO*/
    @Override
    public Map<Card, Integer> getCollentionOwn(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.getCollentionOwn(user);
    }
    @Override
    public boolean update() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.update();
    }
    @Override
    public boolean delete() throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.delete();
    }
    @Override
    public Card createRandomCard(User user) throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.createRandomCard(user);
    }
    @Override
    public Card get_last_card(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.get_last_card(user);
    }
    @Override
    public ArrayList<Card> openSachet(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.openSachet(user);
    }
    @Override
    public ArrayList<Card> filters(User user, String name, String category, String classCard, String typeCard) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.filters(user, name, category, classCard, typeCard);
    }
    /*EXCHANGE CARD DAO*/
    @Override
    public void create(User user, Map<Integer, Integer> cardown, Map<Integer, Integer> cardwanted) throws SQLException {
        ExchangeCardDAO exchangeCardDAO=new ExchangeCardDAOImpl();
        exchangeCardDAO.create(user,cardown,cardwanted);
    }
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.marketExchange(exchangeCard);
    }
    @Override
    public void delete(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        f.delete(id_trans);
    }
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getExchange(id_trans);

    }
    @Override
    public ArrayList<Exchange> getAllExchange(User user, String Parameter) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getAllExchange(user, Parameter);
    }
    @Override
    public ArrayList<Exchange> filtersexchange(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.filtersexchange(user, name, category, classCard, typeCard);
    }
    /*USER DAO*/
    @Override
    public boolean save(User user, String pass) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.save(user, pass);
    }
    @Override
    public User findByUsername(String username) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findByUsername(username);
    }
    @Override
    public boolean update(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.update(user);
    }
    @Override
    public boolean delete(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.delete(user);
    }
    @Override
    public boolean checkByUser(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkByUser(user);
    }
    @Override
    public ArrayList<User> findAll() throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findAll();
    }
    @Override
    public boolean checkUnique(User user) {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkUnique(user);
    }
}
