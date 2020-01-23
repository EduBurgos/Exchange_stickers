package test.dao;

import collection.Card;
import dao.CardsDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DaoCardTest {
    private int id = 999;
    private String categoria = "categoriaTest";
    private String classe = "classeTest";
    private int livello = 999;
    private String rarita = "raritàTest";
    private String tipo = "tipoTest";
    private String nome = "nomeTest";
    private String descrizione = "descrizioneTest";

    CardsDaoImpl cardDaoTest = new CardsDaoImpl();
    Card testCard = new Card(id, categoria, classe, livello, rarita, tipo, nome, descrizione);

    @BeforeAll
    void insertTest() throws SQLException {
        Card testCard = new Card(id, categoria, classe, livello, rarita, tipo, nome, descrizione);

        assertEquals(true, cardDaoTest.insert(testCard));

    }

    @Test
    void updateTest() throws SQLException {
        String categoria2 = "categoriaTest2";
        String classe2 = "classeTest2";
        int livello2 = 666;
        String rarita2 = "raritàTest2";
        String tipo2 = "tipoTest2";
        String nome2 = "nomeTest2";
        String descrizione2 = "descrizioneTest2";

        Card testCard2 = new Card(id, categoria2, classe2, livello2, rarita2, tipo2, nome2, descrizione2);

        assertEquals(true, cardDaoTest.update(testCard2));

    }

    @Test
    void findByIdTest(){
        try {
            Card card = cardDaoTest.findByID(999);
            assertNotNull(card);
            assertEquals(true, card instanceof Card);
            assertEquals(true, card.equals(testCard));
        } catch (SQLException e) {
            fail("error due to sql exception\n"+e.getStackTrace());
        }

    }

}
