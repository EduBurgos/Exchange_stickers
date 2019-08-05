package dao;

import userSide.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {
    //CRUD
    boolean save(User user) throws SQLException;
    User findByUsername(String username) throws SQLException;
    boolean update(User user) throws SQLException;
    boolean delete(User user) throws SQLException;
    boolean checkByUser(User user) throws SQLException;
    ArrayList<User> findAll() throws SQLException;
}
