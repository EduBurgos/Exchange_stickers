package dao;

import userSide.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDaoImpl implements FriendshipDao {

    MySQLDAOFactory connector = MySQLDAOFactory.getInstance();

    @Override
    public boolean sendRequest(String sender, String receiver) {
        String sql = "INSERT IGNORE INTO friendships (sender, receiver) VALUES (?, ?)";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean acceptRequest(int id, String receiver) {
        String sql = "UPDATE friendships SET status='accepted' WHERE id=? AND receiver=? AND status='pending'";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, receiver);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean declineRequest(int id, String receiver) {
        String sql = "DELETE FROM friendships WHERE id=? AND receiver=? AND status='pending'";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, receiver);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getFriends(String username) {
        List<User> friends = new ArrayList<>();
        String sql = "SELECT u.Username, u.NameUser, u.Surname, u.Mail FROM friendships f " +
                     "JOIN users u ON (f.sender=u.Username OR f.receiver=u.Username) " +
                     "WHERE (f.sender=? OR f.receiver=?) AND f.status='accepted' AND u.Username!=?";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                friends.add(new User(rs.getString("NameUser"), rs.getString("Surname"),
                                     rs.getString("Username"), rs.getString("Mail")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public List<Object[]> getPendingRequests(String username) {
        List<Object[]> requests = new ArrayList<>();
        String sql = "SELECT f.id, u.Username, u.NameUser, u.Surname FROM friendships f " +
                     "JOIN users u ON f.sender=u.Username " +
                     "WHERE f.receiver=? AND f.status='pending'";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                requests.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("Username"),
                    rs.getString("NameUser") + " " + rs.getString("Surname")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public boolean areFriends(String user1, String user2) {
        String sql = "SELECT 1 FROM friendships WHERE " +
                     "((sender=? AND receiver=?) OR (sender=? AND receiver=?)) AND status='accepted'";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user1); ps.setString(2, user2);
            ps.setString(3, user2); ps.setString(4, user1);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean requestExists(String sender, String receiver) {
        String sql = "SELECT 1 FROM friendships WHERE " +
                     "((sender=? AND receiver=?) OR (sender=? AND receiver=?))";
        try (Connection conn = connector.createConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sender); ps.setString(2, receiver);
            ps.setString(3, receiver); ps.setString(4, sender);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
