package test;

import dao.CardsDaoImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class QueryTest {

        @Test
        public void findAll(){
            CardsDaoImpl x = new CardsDaoImpl();
            try{
                x.findAllGeneric();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
