package dao;

import userSide.User;
import java.util.List;

public interface FriendshipDao {
    boolean sendRequest(String sender, String receiver);
    boolean acceptRequest(int id, String receiver);
    boolean declineRequest(int id, String receiver);
    List<User> getFriends(String username);
    List<Object[]> getPendingRequests(String username); // {id, senderUsername}
    boolean areFriends(String user1, String user2);
    boolean requestExists(String sender, String receiver);
}
