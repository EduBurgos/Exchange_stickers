package dao;

import com.mysql.jdbc.Statement;
import userSide.Exchange;
import userSide.User;

import java.sql.*;
import java.util.*;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class ExchangeCardDAOImpl implements ExchangeCardDAO {
    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;

    private static final String insert_transaction_query = "INSERT INTO exchanges (username) VALUES (?)";
    private static final String insert_cardown_query = "insert into cards_own(id_trans,cardId,quantity) values (?,?,?)";
    private static final String insert_cardWanted_query = "insert into cards_wanted(id_trans,cardId,quantity) values (?,?,?)";

    private static final String selectCollection = "select * from collections where ID_Card=? AND USERNAME=?";
    private static final String switchpeople = "insert into collections (ID_CARD,Username) values (?,?) "+
                                                    "ON DUPLICATE KEY UPDATE quantity=quantity+1";
    private static final String deleteCollection = "delete FROM collections WHERE Username=? and quantity=1 and ID_CARD=?";
    private static final String updateCollection = "update collections SET  quantity=quantity-1 WHERE  ID_CARD=? AND USERNAME=?";

    //transaction for update flag
    private static final String select_transaction = "select * from exchanges where id_trans=? and trans_comp='0'";
    private static final String flag_complete = "update exchanges SET  USERNAME_Offer=? , trans_comp='1' WHERE id_trans=? AND trans_comp='0'";


    private static final String get_exchange ="select exchanges.* , cards_own.cardId from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=?  ";
    private static final String get_all_my_exchange ="select exchanges.* , cards_own.cardId as own,cards_own.quantity as oqt, cards_wanted.cardId as want, cards_wanted.quantity as wqt from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans) join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans where username=? order by exchanges.id_trans";
    private static final String get_cardWanted="select cards_wanted.* from (exchanges join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=? ";
    private static final String get_all_exchange ="select exchanges.*,cards_wanted.cardId as want,cards_own.cardId as own,cards_own.quantity as oqt,cards_wanted.quantity as wqt from exchanges  join  cards_wanted on exchanges.Id_trans=cards_wanted.id_trans join cards_own on exchanges.Id_trans=cards_own.Id_trans  where exchanges.id_trans not in (select exchanges.Id_trans  from cards_wanted,collections,exchanges where cards_wanted.cardId not in (select collections.ID_Card from collections WHERE USERNAME=? and quantity >= cards_wanted.quantity)  and exchanges.id_trans=cards_wanted.Id_trans and exchanges.username!=? group by exchanges.id_trans)and username!=? and trans_comp=?";
    private static final String get_exchange_to_notify="select exchanges.* , cards_own.cardId as own,cards_own.quantity as oqt, cards_wanted.cardId as want, cards_wanted.quantity as wqt from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans) join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans where username=? and trans_comp=1 and notified=0 order by exchanges.id_trans";
    private static final String delete_exchange = "delete from exchanges where id_trans=?";
    private static final String set_exchange_notified = "update exchanges SET  notified='1' WHERE id_trans=? ";
    private FacadeImplements f=new FacadeImplements();


    /**
     * allows the user to create an exchange by selecting the cards he would like
     * to give and the cards he would like to receive
     * @param user  the user who creates the exchange
     * @param cardown  id and quantity of the cards the user would like to give
     * @param cardwanted  id and quantity of the cards the user would like to have
     * @return the id that identifies the created exchange
     * @throws SQLException exception caused by database access error
     * */

    @Override
    public int create(User user, Map<Integer,Integer> cardown, Map<Integer,Integer> cardwanted) throws SQLException {
        conn = null;
        int exchangeKey=0;
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(insert_transaction_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();

            result=preparedStatement.getGeneratedKeys();

            if (result.next() && result!=null){
                exchangeKey = result.getInt(1);
                System.out.println("la chiave dello scambio è la seguente: "+exchangeKey);
                for (int c: cardown.keySet()) {
                    preparedStatement=conn.prepareStatement(insert_cardown_query);
                    preparedStatement.setInt(1,result.getInt(1));
                    preparedStatement.setInt(2,c);
                    preparedStatement.setInt(3, cardown.get(c));
                    preparedStatement.execute();
                }
                for (int d: cardwanted.keySet()) {
                    preparedStatement=conn.prepareStatement(insert_cardWanted_query);
                    preparedStatement.setInt(1,result.getInt(1));
                    preparedStatement.setInt(2,d);
                    preparedStatement.setInt(3, cardwanted.get(d));
                    preparedStatement.execute();
                }
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return exchangeKey;
    }
    /**
     * Allows the user who has the required cards to accept the exchange created by another user
     * @param exchangeCard interested exchange
     * @return true if the exchange has been successfully accepted, false otherwise
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        conn = null;
        Savepoint savepoint = null;
        List<Savepoint> insert = new LinkedList<>();
        List<Savepoint> insert2 = new LinkedList<>();
        try {
                //seleziono il record della transazione e creo un savepoint a cui fare rollback in caso di errori
                conn = connector.createConnection();
                preparedStatement = conn.prepareStatement(select_transaction);
                //start transaction
                conn.setAutoCommit(false);
                //transaction level serializable
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);

                preparedStatement.setInt(1,exchangeCard.getId_trans());
                preparedStatement.execute();
                result=preparedStatement.getResultSet();
                savepoint = conn.setSavepoint();
                //se non trovo la transazione vuol dire che qualcuno l'ha già accettata quindi esco dal metodo con false
            if (!result.isBeforeFirst()) {
                return false;
            }
            //setto flag completato = 1 per evitare che altri utenti accettino lo stesso scambio
                preparedStatement=conn.prepareStatement(flag_complete);
                preparedStatement.setString(1,exchangeCard.getUsername_offerente());
                preparedStatement.setInt(2,exchangeCard.getId_trans());
                preparedStatement.executeUpdate();

                //da all'utente le mie carte che vuole
                for(int i=0;i<exchangeCard.getId_card_wanted().size();i++)
                {
                    //blocco le carte selezionandole e creo un savepoint a cui tornare
                    preparedStatement = conn.prepareStatement(selectCollection);
                    preparedStatement.setInt(1,exchangeCard.getId_card_wanted().get(i));
                    preparedStatement.setString(2,exchangeCard.getUsername_offerente());
                    preparedStatement.execute();
                    insert.add(conn.setSavepoint());

                    //se la mia carta è già nella collezione dell'acquirente  incremento la quantità nella sua collezione con "on duplicate key"
                    preparedStatement = conn.prepareStatement(switchpeople);
                    preparedStatement.setInt(1,exchangeCard.getId_card_wanted().get(i));
                    preparedStatement.setString(2,exchangeCard.getId_user());
                    preparedStatement.execute();

                    //cancello dalla  collezione del venditore la carta appena ceduta (se ne possiede solo una)
                    preparedStatement=conn.prepareStatement(deleteCollection);
                    preparedStatement.setString(1,exchangeCard.getUsername_offerente());
                    preparedStatement.setInt(2,exchangeCard.getId_card_wanted().get(i));
                    preparedStatement.execute();
                    conn.commit();

                    //se, invece, ne possiede più di una faccio un decremento del campo quantità
                    preparedStatement=conn.prepareStatement(updateCollection);
                    preparedStatement.setInt(1,exchangeCard.getId_card_wanted().get(i));
                    preparedStatement.setString(2,exchangeCard.getUsername_offerente());
                    preparedStatement.execute();
                    conn.commit();
                }
                //da al venditore le carte dell'acquirente
                for (int i=0;i<exchangeCard.get_id_card_owm().size();i++)
                {
                    //blocco le carte e creo un savepoint della collezione dell'acquirente(uno per ogni ciclo)
                    preparedStatement = conn.prepareStatement(selectCollection);
                    preparedStatement.setInt(1,exchangeCard.get_id_card_owm().get(i));
                    preparedStatement.setString(2,exchangeCard.getId_user());
                    preparedStatement.execute();
                    insert2.add(conn.setSavepoint());

                    //se la carta è già nella collezione del venditore incrementa tramite on duplicate key
                    preparedStatement = conn.prepareStatement(switchpeople);
                    preparedStatement.setInt(1,exchangeCard.get_id_card_owm().get(i));
                    preparedStatement.setString(2,exchangeCard.getUsername_offerente());
                    preparedStatement.execute();

                    //cancello dalla collezione dell'acquirente la carta se ne possiede soltanto una
                    preparedStatement=conn.prepareStatement(deleteCollection);
                    preparedStatement.setString(1,exchangeCard.getId_user());
                    preparedStatement.setInt(2,exchangeCard.get_id_card_owm().get(i));
                    preparedStatement.execute();
                    //conn.commit();

                    //altrimenti decremento la quantità
                    preparedStatement=conn.prepareStatement(updateCollection);
                    preparedStatement.setInt(1,exchangeCard.get_id_card_owm().get(i));
                    preparedStatement.setString(2,exchangeCard.getId_user());
                    preparedStatement.execute();
                    //salvo le modifiche della transazione
                    conn.commit();
                }
                //riporta il db alla configurazione iniziale
                conn.setAutoCommit(true);

            }
        catch (SQLException e)
        {
            //in caso di errore eseguo dei rollback per tornare allo stato precedente alle modifiche
            try {
                if(savepoint!=null) {
                    conn.rollback(savepoint);
                }
                for (Savepoint s:insert) {
                    conn.rollback(s);
                }
                for (Savepoint s:insert2) {
                    conn.rollback(s);
                }

            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
                System.out.println("ERROR DURING ROLLBACK");
            }
            System.out.println("ERROR DURING EXCHANGE, ROLLBACK EXECUTED");
            return false;
        }
        return true;
    }

    /**
     * Finds exchange by its id
     * @param id_trans  id of the exchange searched
     * @return exchange found
     */
    @Override
    public Exchange getExchange(int id_trans){
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(get_exchange);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            ArrayList<Integer> cardown =new ArrayList<>();
            ArrayList<Integer> cardwanted= new ArrayList<>();

            boolean firstStep=true;
            String usernameExchangeSetter ="";
            while (result.next()) {
                if (firstStep){
                    usernameExchangeSetter = result.getString("username");
                    firstStep = false;
                }
                cardown.add(result.getInt("cardId"));
            }
            preparedStatement = conn.prepareStatement(get_cardWanted);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();


            while (result.next()) {
                cardwanted.add(result.getInt("cardId"));
            }
            return new Exchange(id_trans, usernameExchangeSetter, cardown,cardwanted);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return null;
    }

    /**Takes, based on the parameters,  exchanges created by the user  or all the exchanges the logged user can accept
     *
     * @param user the logged user
     * @param parameter (switch clause) "mine" to get all user's exchanges - "all" to get exchenges he can accept -
     *                     notify to exchanges not notified,yet
     * @return exchanges created by the logged user  or all the exchanges they can accept
     * @throws SQLException exception caused by database access error
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user,String parameter) throws SQLException {
        conn=null;
        ArrayList<Integer> cardown = new ArrayList<>();
        ArrayList<Integer> cardwanted = new ArrayList<>();
        ArrayList<Exchange> allExchange = new ArrayList<>();
        conn = connector.createConnection();

        //switch per decidere quale query utilizzare
        switch (parameter)
                {
                    case "mine":
                    {
                        //prendo tutte le trattative che l'utente ha realizzato
                        preparedStatement = conn.prepareStatement(get_all_my_exchange);
                        preparedStatement.setString(1, user.getUsername());
                    }
                    break;
                    case "all":
                    {
                        //prendo tutte le trattative a cui l'utente può accedere ( deve possedere le carte che il venditore vuole in cambio) il controllo è fatto all'interno della query
                        preparedStatement = conn.prepareStatement(get_all_exchange);
                        preparedStatement.setString(1, user.getUsername());
                        preparedStatement.setString(2, user.getUsername());
                        preparedStatement.setString(3, user.getUsername());
                        preparedStatement.setBoolean(4,false); //mostra solo le transazioni non completate
                    }
                    break;
                    case "notify":
                    {
                        preparedStatement = conn.prepareStatement(get_exchange_to_notify);
                        preparedStatement.setString(1, user.getUsername());
                    }
                }
        try {


            // preparedStatement.setString(2, user.getUsername());
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            int id_trans=0;
            boolean trans_compl=false;
            String username;
            String username_offer;
            //inizio la lettura del result set
            if(result!=null && result.next()) {
                //salvo il primo id transazione per sapere quando settare un nuovo scambio
                id_trans = result.getInt("id_trans");
                addCard(result.getInt("oqt"), cardown, result.getInt("own"));
                addCard(result.getInt("wqt"), cardown, result.getInt("want"));
            }
            while (result != null) {
                cardown = new ArrayList<Integer>();
                cardwanted = new ArrayList<Integer>();
                username = result.getString("username");
                username_offer = result.getString("username_offer");
                trans_compl = result.getBoolean("trans_comp");

                //finchè l'id_trans contenuto nel result_set è uguale a quello salvato non crea una nuovo scambio,
                // quando l'id cambia lo scambio viene aggiunto alla lista
                while (result.getInt("id_trans") == id_trans) {
                    // controlla se la carta è già nella lista cardown, se non lo è l'aggiunge
                    //il controllo è necessario perchè il result set è dato da più join
                    if (!checkCard(cardown, result.getInt("own"))) {
                        //cardown.add(result.getInt("own"));
                        addCard(result.getInt("oqt"), cardown, result.getInt("own"));
                    }
                    // controlla se la carta è già nella lista cardwanted, se non lo è l'aggiunge
                    if (!checkCard(cardwanted, result.getInt("want"))) {
                       //cardwanted.add(result.getInt("want"));
                        //aggiungo n volte la stessa carta nella lista dove n è dato da wqt
                        addCard(result.getInt("wqt"), cardwanted, result.getInt("want"));
                    }
                    //se ho analizzato tutto il resltSet esco dal ciclio
                    if(result.next()==false)
                    {
                        result=null;
                        break;
                    }
                }
                //aggiungo scambio alla lista
                allExchange.add(new Exchange(id_trans,username, cardown, cardwanted, trans_compl, username_offer));
                if(result==null)
                {
                    break;
                }
                else
                {
                    //salvo il nuovo id_transazione e ritorno nel while
                    id_trans = result.getInt("id_trans");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return allExchange;
    }

   /**
    * Allows the user to delete the exchanges created by them
    * @param id_trans id that identifies the exchange
    */
   @Override
    public void delete(int id_trans) {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(delete_exchange);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
    }

    /**
     * Allows the logged user to find certain available exchanges,except for the ones started by logged user,
     *  through the filters of the search bar.
     * @param user logged user
     * @param name  the name of the card offered that belongs to searched exchange.
     * @param category  the category of the card offered that belong to searched exchange.
     * @param classCard class of the card offered that belong to searched exchange.
     * @param typeCard  type of the card offered that belong to searched exchange.
     * @return the requested exchanges from logged user.
     * */
    public ArrayList<Exchange>filtersExchange(User user, String name, String category , String classCard, String typeCard){
        conn=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        int j=5;
       String get_all_exchangeFilter ="select exchanges.*,cards_wanted.cardId as want,cards_own.cardId as own,cards_own.quantity as oqt,cards_wanted.quantity as wqt from (exchanges  join  cards_wanted on exchanges.Id_trans=cards_wanted.id_trans join cards_own on exchanges.Id_trans=cards_own.Id_trans) join catalog ON cards_own.cardId=catalog.ID  where exchanges.id_trans not in (select exchanges.Id_trans  from cards_wanted,collections,exchanges where cards_wanted.cardId not in (select collections.ID_Card from collections WHERE USERNAME=? and quantity >= cards_wanted.quantity)  and exchanges.id_trans=cards_wanted.Id_trans and exchanges.username!=? group by exchanges.id_trans)and username!=? and trans_comp=?";

        ArrayList<Integer> cardown = new ArrayList<>();
        ArrayList<Integer> cardwanted=new ArrayList<>();

                try {
                conn = connector.createConnection();
                    preparedStatement = conn.prepareStatement(f.completeQuery(get_all_exchangeFilter,name,category,classCard,typeCard));
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getUsername());
                    preparedStatement.setString(3, user.getUsername());
                    preparedStatement.setBoolean(4,false);
                    f.setQuery(j,preparedStatement,name,category,classCard,typeCard);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();

                    int id_trans=0;
                    boolean trans_compl;
                    String username, username_offer;
                    if(result!=null && result.next()) {
                        id_trans = result.getInt(1);
                        cardown.add(result.getInt("own"));
                        cardwanted.add(result.getInt("want"));
                    }
                    while (result != null) {
                        cardown = new ArrayList<>();
                        cardwanted = new ArrayList<>();
                        username = result.getString("username");
                        username_offer = result.getString("username_offer");
                        trans_compl = result.getBoolean("trans_comp");
                        while (result.getInt(1) == id_trans) {

                            if (!checkCard(cardwanted, result.getInt("want"))) {
                                cardwanted.add(result.getInt("want"));
                                addCard(result.getInt("wqt"), cardown, result.getInt("want"));
                            }
                            if (!checkCard(cardown, result.getInt("own"))) {
                                cardown.add(result.getInt("own"));
                                addCard(result.getInt("oqt"), cardown, result.getInt("own"));
                            }
                            if(!result.next())
                            {
                                result=null;
                                break;
                            }
                        }
                        answer.add(new Exchange(id_trans,username, cardown, cardwanted));
                        if(result==null)
                        {
                            break;
                        }
                        else
                        {
                            id_trans = result.getInt("id_trans");
                        }

                    }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
        return answer;
    }

    /**Checks if a card is already contained in a list
     * @param cards list of cards that has to be checked
     * @param toSearch  id of the card to search
     * @return true if the card is found, false if not
     * @see #getAllExchange(User, String)
     */
    public boolean checkCard(ArrayList<Integer> cards, int toSearch)
    {
        //metodo che controlla se elemento è già presente nell'array
                if (cards.contains(toSearch))
                    return true;
            return false;
    }

    /**
     * Adds n equals card into a list
     * @param quantity  number of cards to add
     * @param cardsArray list where cards have to be added
     * @param card id of the card to add
     */
    public void addCard(int quantity, ArrayList<Integer> cardsArray, int card)
        {
            //if(quantity>1)
            //{
               while (quantity!=0)
                {
                    cardsArray.add(card);
                    quantity--;
                }
            //}
        }


    /**
     * Sets on DB the exchange as notified
     * @param exchange exchange that has to be set as notified
     */
    public void setExchangeNotified(Exchange exchange){
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(set_exchange_notified);
            preparedStatement.setInt(1, exchange.getId_trans());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
                rse.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
                sse.printStackTrace();
            }
            try {
                conn.close();
            } catch (Exception cse) {
                cse.printStackTrace();
            }
        }
    }
}
