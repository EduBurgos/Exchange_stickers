package collection;

import java.io.IOException;
import java.util.ArrayList;

//TODO CLASSE NON IMPLEMENTATA
public class Catalogue
{
    //lista di carte contenute nella piattaforma,l'utente sceglie le carte della sua collezione dal catalogo
    //to do: lista di carte
    //proposte: aggiungi invia segnalazione per add carta non in catalogo

    private static final String view_catalog_query = "select * from catalog";


    private ArrayList<Card> carte;
    public Catalogue() {
        this.carte = new ArrayList<Card>();
    }

    public ArrayList<Card> getCarte() {
        return carte;
    }


    /** Given the ID card the card is added to collection
     * @see Card
     * @param id card
     * @return Card which is added to collection
     */
    public Card getCard(int id) {
        for (Card c: carte) {
            if (c.getId()==id)
            {
               return c;
            }
        }
        return null;
    }
    // metodo per riempire catalogo
    public void riempiCatalogo(String filename) throws IOException{
        //Reader r= new Reader();
        //carte.addAll(r.readInfo(filename));
    }
}
