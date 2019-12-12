package test;


import org.junit.jupiter.api.Test;
import userSide.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private String nomeProva = "nomeProva";
    private String cognomeProva = "cognomeProva";
    private String usernameProva = "usernameProva";
    private String mail = "mailProva";



    @Test
    public User userCreation(){
        return new User(nomeProva, cognomeProva, usernameProva, mail);
    }

    @Test
    public void equalUser(){
        User user1 = new User(nomeProva, cognomeProva, usernameProva, mail);
        User user2 = new User(nomeProva, cognomeProva, usernameProva, mail);
        assertEquals(true, user1.equals(user2));
    }
}