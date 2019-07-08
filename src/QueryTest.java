import dao.CardDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

public class QueryTest {

        @Test(expected = NullPointerException.class)
        public void findAll(){
            CardDaoImpl x = new CardDaoImpl();
            try{
                x.findAll();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
