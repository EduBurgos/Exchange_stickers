package dao;

import collection.Card;
import collection.CollectionOwn;
import userSide.Exchange;
import userSide.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FacadeImplements implements Facade
{
    /*CARDSDAO IMPLEMENT*/
    /**
     * @see CardsDaoImpl#insert(Card)
     */
    @Override
    public boolean insert(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.insert(card);
    }
    /**
     * @see CardsDaoImpl#findByID(int)
     */
    @Override
    public Card findByID(int id) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.findByID(id);
    }

    /**
     * @see CardsDaoImpl#filterCatalog(String, String, String, String)
     */

    @Override
    public ArrayList<Card> filterCatalog(String nameCard, String category, String classCard, String typeCard) throws SQLException{
        CardsDaoImpl c=new CardsDaoImpl();
        return c.filterCatalog(nameCard,category,classCard,typeCard);
    }

    /**
     * @see CardsDaoImpl#update(Card)
     */
    @Override
    public boolean update(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return  c.update(card);
    }
    /**
     * @see CardsDaoImpl#delete(Card)
     */
    @Override
    public boolean delete(Card card) throws SQLException {
        CardsDaoImpl c = new CardsDaoImpl();
        return c.delete(card);
    }
    /**
     * @see CardsDaoImpl#findAllGeneric()
     */
    @Override
    public ArrayList<Card> findAllGeneric() throws SQLException {
        CardsDaoImpl f = new CardsDaoImpl();
        return f.findAllGeneric();
    }

    /*COLLECTION ONW DAO*/

    /**
     * @see CollectionOwnDaoImpl#getCollentionOwn(User)
     */
    @Override
    public CollectionOwn getCollentionOwn(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.getCollentionOwn(user);
    }
    /**
     * @see CollectionOwnDaoImpl#createRandomCard(User)
     */
    @Override
    public Card createRandomCard(User user){
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.createRandomCard(user);
    }
    /**
     * @see CollectionOwnDaoImpl#get_last_card(User)
     */
    @Override
    public Card get_last_card(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.get_last_card(user);
    }

    // TODO: DA CANCELLARE!

   // @Override
    public ArrayList<Card> openSachet(User user) {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.openSachet(user);
    }
    /**
     * @see CollectionOwnDaoImpl#filters(User, String, String, String, String)
     */
    @Override
    public ArrayList<Card> filters(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        CollectionOwnDaoImpl f = new CollectionOwnDaoImpl();
        return f.filters(user, name, category, classCard, typeCard);
    }

    /**
     * @see CollectionOwnDaoImpl#giftCard(User)
     */
    @Override
    public boolean giftCard(User user){
        CollectionOwnDao f = new CollectionOwnDaoImpl();
        return f.giftCard(user);
    }

    /*EXCHANGE CARD DAO*/
    /**
     * @see ExchangeCardDAOImpl#create(User, Map, Map)
     */
    @Override
    public int create(User user, Map<Integer, Integer> cardown, Map<Integer, Integer> cardwanted) throws SQLException {
        ExchangeCardDAO exchangeCardDAO=new ExchangeCardDAOImpl();
        return exchangeCardDAO.create(user,cardown,cardwanted);
    }
    /**
     * @see ExchangeCardDAOImpl#marketExchange(Exchange)
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.marketExchange(exchangeCard);
    }
    /**
     * @see ExchangeCardDAOImpl#delete(int)
     */
    @Override
    public void delete(int id_trans){
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        f.delete(id_trans);
    }
    /**
     * @see ExchangeCardDAOImpl#getExchange(int)
     */
    @Override
    public Exchange getExchange(int id_trans){
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getExchange(id_trans);

    }
    /**
     * @see ExchangeCardDAOImpl#getAllExchange(User, String)
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user, String Parameter) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.getAllExchange(user, Parameter);
    }
    /**
     * @see ExchangeCardDAOImpl#filtersExchange(User, String, String, String, String)
     */
    @Override
    public ArrayList<Exchange> filtersExchange(User user, String name, String category, String classCard, String typeCard) throws SQLException {
        ExchangeCardDAOImpl f = new ExchangeCardDAOImpl();
        return f.filtersExchange(user, name, category, classCard, typeCard);
    }

    /*USER DAO*/
    /**
     * @see UserDaoImpl#save(User, String)
     */
    @Override
    public boolean save(User user, String pass) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.save(user, pass);
    }
    /**
     * @see UserDaoImpl#findByUsername(String)
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findByUsername(username);
    }
    /**
     * @see UserDaoImpl#update(User)
     */
    @Override
    public boolean update(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.update(user);
    }
    /**
     * @see UserDaoImpl#delete(User)
     */
    @Override
    public boolean delete(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.delete(user);
    }
    /**
     * @see UserDaoImpl#checkByUser(User)
     */
    @Override
    public boolean checkByUser(User user) throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkByUser(user);
    }
    /**
     * @see UserDaoImpl#findAll()
     */
    @Override
    public ArrayList<User> findAll() throws SQLException {
        UserDaoImpl f = new UserDaoImpl();
        return f.findAll();
    }
    /**
     * @see UserDaoImpl#checkUnique(User)
     */
    @Override
    public boolean checkUnique(User user) {
        UserDaoImpl f = new UserDaoImpl();
        return f.checkUnique(user);
    }
    /**
     * @see ExchangeCardDAOImpl#setExchangeNotified(Exchange)
     */
    @Override
    public void setExchangeNotified(Exchange exchange) throws SQLException {
        ExchangeCardDAO temp = new ExchangeCardDAOImpl();
        temp.setExchangeNotified(exchange);
    }

    /**
     * Based on the parameters inserted, the parameter query is completed
     * @param query a String. Indicates query that is going to be modified
     * @param nameCard a String. Indicates the name of the card searched
     * @param category a String. Indicates the category of the card searched
     * @param classCard a String. Indicates the class of the card searched
     * @param typeCard a String. Indicates the type of the card searched
     * @return String. The final query used in filter methods
     */
    public String completeQuery( String query, String nameCard,String category, String classCard, String typeCard) {
        if (!nameCard.equals("") || category != null || !classCard.equals("") || !typeCard.equals("")) {

            if (!nameCard.equals("")) {
                query += " AND CardName=?";
            }
            if (category != null) {
                query += " AND Category=?";
            }
            if (!classCard.equals("")) {
                query += " AND Class=?";
            }
            if (!typeCard.equals("")) {
                query += " AND CardType =?";
            }
        }
        return query;
    }

    /**
     * Based on the inserted parameters, it sets parameters of preparedStatement
     * @param counter a int. Indicates a counter used to set the parameters
     * @param p PreparedStatement in which its parameters are set
     * @param name a String. Indicates the name of the card searched
     * @param category a String. Indicates the category of the card searched
     * @param classCard a String. Indicates the class of the card searched
     * @param typeCard a String. Indicates the type of the card searched
     * @return PreparedStatement where its parameters are set
     * @throws SQLException exception caused by database access error
     */
    public PreparedStatement setQuery(int counter, PreparedStatement p, String name, String category, String classCard, String typeCard) throws SQLException{

        if (!name.equals("")) {
            p.setString(counter, name);
            counter++;
        }
        if (category != null) {
            p.setString(counter, category);
            counter++;
        }
        if (!classCard.equals("")) {
            p.setString(counter, classCard);
            counter++;
        }
        if (!typeCard.equals("")) {
            p.setString(counter, typeCard);
            counter++;
        }
        return p;
    }
}
