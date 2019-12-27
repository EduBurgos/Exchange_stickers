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
    private static final String flag_complete = "update exchanges SET  USERNAME_Offer=? , trans_comp='1' WHERE id_trans=? ";


    private static final String get_exchange ="select exchanges.* , cards_own.cardId from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=?  ";
    private static final String get_all_my_exchange ="select exchanges.* , cards_own.cardId as own,cards_own.quantity as oqt, cards_wanted.cardId as want, cards_wanted.quantity as wqt from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans) join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans where username=? order by exchanges.id_trans";
    private static final String get_cardWanted="select cards_wanted.* from (exchanges join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans)  where exchanges.id_trans=? ";
    private static final String get_all_exchange ="select exchanges.*,cards_wanted.cardId as want,cards_own.cardId as own,cards_own.quantity as oqt,cards_wanted.quantity as wqt from exchanges  join  cards_wanted on exchanges.Id_trans=cards_wanted.id_trans join cards_own on exchanges.Id_trans=cards_own.Id_trans  where exchanges.id_trans not in (select exchanges.Id_trans  from cards_wanted,collections,exchanges where cards_wanted.cardId not in (select collections.ID_Card from collections WHERE USERNAME=? and quantity >= cards_wanted.quantity)  and exchanges.id_trans=cards_wanted.Id_trans and exchanges.username!=? group by exchanges.id_trans)and username!=? and trans_comp=?";
    private static final String get_exchange_to_notify="select exchanges.* , cards_own.cardId as own,cards_own.quantity as oqt, cards_wanted.cardId as want, cards_wanted.quantity as wqt from (exchanges join cards_own ON cards_own.Id_trans=exchanges.Id_trans) join cards_wanted ON cards_wanted.Id_trans=exchanges.Id_trans where username=? and trans_comp=1 and notified=0 order by exchanges.id_trans";


    private static final String delete_exchange = "delete from exchanges where id_trans=?";
    //private static final String view_catalog = "select * from catalog";
    private static final String set_exchange_notified = "update exchanges SET  notified='1' WHERE id_trans=? ";





    @Override
    public void create(User user, Map<Integer,Integer> cardown, Map<Integer,Integer> cardwanted) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();

            preparedStatement = conn.prepareStatement(insert_transaction_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.execute();

            result=preparedStatement.getGeneratedKeys();

            if (result.next() && result!=null)
            {
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
     * Method used to  exchange cards
     * @param exchangeCard
     * @return true/false
     */
    @Override
    public boolean marketExchange(Exchange exchangeCard) {
        conn = null;
        Savepoint savepoint = null;
        List<Savepoint> insert = new LinkedList<>();
        List<Savepoint> insert2 =new LinkedList<>();
        //cancello carte presenti in entrambe le liste perchè non ha senso scambiare due carte uguali
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
                if(result==null && !result.next()) {
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
                conn.rollback(savepoint);
                for (Savepoint s:insert) {
                    conn.rollback(s);
                }
                for (Savepoint s:insert2) {
                    conn.rollback(s);
                }

            }
            catch (SQLException e1)
            {

            }

            return true;
        }
        return false;


    }

    //inutile ho un ogegtto che è una lista di scambi... basta passarlo

    /**
     * Method used to get exchange by its id
     * @param id_trans a int. Indicates id of the exchange searched
     * @return Exchange
     * @throws SQLException
     */
    @Override
    public Exchange getExchange(int id_trans) throws SQLException {
        conn = null;
        try {
            conn = connector.createConnection();
            preparedStatement = conn.prepareStatement(get_exchange);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();
            ArrayList<Integer> cardown =new ArrayList<>();
            ArrayList<Integer> cardwanted= new ArrayList<>();

            while (result.next()) {
                cardown.add(result.getInt("cardId"));
            }
            preparedStatement = conn.prepareStatement(get_cardWanted);
            preparedStatement.setInt(1, id_trans);
            preparedStatement.execute();
            result = preparedStatement.getResultSet();


            while (result.next()) {
                cardwanted.add(result.getInt("cardId"));
            }
            return new Exchange(id_trans, result.getString("username"), cardown,cardwanted);
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
            return null;
        }
    }

    /**this methos takes all user's exchanges or all the exchanges that he can accept
     *
     * @param user
     * @param parameter (switch clause) "mine" to get all user's exchanges - "all" to get exchenges he can accept -
     *                     notify to exchanges not notified,yet
     * @return ArrayList<Exchanges>
     * @throws SQLException
     */
    @Override
    public ArrayList<Exchange> getAllExchange(User user,String parameter) throws SQLException {
        conn=null;
        ArrayList<Integer> cardown = new ArrayList<>();
        ArrayList<Integer> cardwanted=new ArrayList<>();
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
                cardown=new ArrayList<Integer>();
                cardwanted=new ArrayList<Integer>();
                username=result.getString("username");
                username_offer=result.getString("username_offer");
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
   @Override
    public void delete(int id_trans) throws SQLException {
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
     * Method that allows the logged user to find certain available exchanges,except for the ones starded by logged user,
     *  through the filters of the search bar.
     * <p>
     *     depending to parameters specified, the original query is completed in such a way that
     *     it can returned the requested exchanges
     * </p>
     * @param user type User. Indicates logged user
     * @param name  a String. Indicates name of the card that belongs to searched exchange.
     * @param category  a String. Indicates category of the card that belong to searched exchange.
     * @param classCard a String. Indicates class of the card that belong to searched exchange.
     * @param typeCard  a String. Indicates type of the card that belong to searched exchange.
     * @return ArrayList<Exchange> that contains the requested exchanges from logged user.
     * @throws SQLException
     * */
    public ArrayList<Exchange>filtersexchange(User user, String name, String category , String classCard, String typeCard) throws SQLException{
        conn=null;
        ArrayList<Exchange> answer= new ArrayList<>();
        int j=5;
       String get_all_exchangeFilter ="select exchanges.*,cards_wanted.cardId as want,cards_own.cardId as own,cards_own.quantity as oqt,cards_wanted.quantity as wqt from (exchanges  join  cards_wanted on exchanges.Id_trans=cards_wanted.id_trans join cards_own on exchanges.Id_trans=cards_own.Id_trans) join catalog ON cards_own.cardId=catalog.ID  where exchanges.id_trans not in (select exchanges.Id_trans  from cards_wanted,collections,exchanges where cards_wanted.cardId not in (select collections.ID_Card from collections WHERE USERNAME=? and quantity >= cards_wanted.quantity)  and exchanges.id_trans=cards_wanted.Id_trans and exchanges.username!=? group by exchanges.id_trans)and username!=? and trans_comp=?";

        ArrayList<Integer> cardown = new ArrayList<>();
        ArrayList<Integer> cardwanted=new ArrayList<>();

                try {
                conn = connector.createConnection();
                if( !name.equals("")|| category!=null || !classCard.equals("") || !typeCard.equals("")) {

                    if (!name.equals("")) {
                        get_all_exchangeFilter += " AND CardName=?";
                    }
                    if (category!=null) {
                        get_all_exchangeFilter += " AND Category=?";
                    }
                    if (!classCard.equals("")) {
                        get_all_exchangeFilter += " AND Class=?";
                    }
                    if (!typeCard.equals("")) {
                        get_all_exchangeFilter += " AND CardType =?";

                    }
                    preparedStatement = conn.prepareStatement(get_all_exchangeFilter);
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getUsername());
                    preparedStatement.setString(3, user.getUsername());
                    preparedStatement.setBoolean(4,false);
                    if (!name.equals("")) {
                        preparedStatement.setString(j, name);
                        j++;
                    }
                    if (category!=null) {
                        preparedStatement.setString(j, category);
                        j++;
                    }
                    if (!classCard.equals("")) {
                        preparedStatement.setString(j, classCard);
                        j++;
                    }
                    if (!typeCard.equals("")) {
                        preparedStatement.setString(j, typeCard);
                        j++;
                    }
                }
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
                        cardown=new ArrayList<>();
                        cardwanted=new ArrayList<>();
                        username=result.getString("username");
                        username_offer=result.getString("username_offer");
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

    /** Method used by "getAllExchanges". It Cheacks if a card is alredy contained in a list
     * @param cards type ArrayList
     * @param toSearch type int (it is the id of the card to search)
     * @return true if cards contains toSearch false if not
     */
    public boolean checkCard(ArrayList<Integer> cards, int toSearch)
    {
        //metodo che controlla se elemento è già presente nell'array
                if (cards.contains(toSearch))
                    return true;
            return false;
    }

    /**
     * Methos who add n equals card into a list
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
/**..................FINE METODI PER LA RICERCA DI TRATTATIVE**/


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
