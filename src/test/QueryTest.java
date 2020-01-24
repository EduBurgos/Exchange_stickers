package test;

import dao.CardsDaoImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class QueryTest {

        @Test
        /**
         * Tests if all cards belonging to the catalog are found
         */
        public void findAll(){
            CardsDaoImpl x = new CardsDaoImpl();
            try{
                x.findAllGeneric();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
