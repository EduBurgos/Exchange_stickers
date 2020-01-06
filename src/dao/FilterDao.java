package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface FilterDao {
    public String completeQuery( String query, String nameCard,String category, String classCard, String typeCard);
    public PreparedStatement setQuery(int counter,PreparedStatement p, String name, String category, String classCard, String typeCard) throws SQLException;
}
