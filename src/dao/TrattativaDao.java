package dao;

import userSide.Trattativa;
import userSide.User;

import java.sql.SQLException;

public interface TrattativaDao {
    //CRUD
    boolean create(Trattativa trattativa) throws SQLException;
    Trattativa find() throws SQLException;
    boolean update() throws SQLException;
    boolean delete() throws SQLException;
}
