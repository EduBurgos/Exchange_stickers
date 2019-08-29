import dao.CardsDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class QueryTest {

        @Test(expected = NullPointerException.class)
        public void findAll(){
            CardsDaoImpl x = new CardsDaoImpl();
            try{
                x.findAllGeneric();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
