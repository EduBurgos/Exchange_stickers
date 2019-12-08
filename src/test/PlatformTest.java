package test;

import collection.Card;
import collection.CollectionOwn;
import dao.CollectionOwnDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import platform.Platform;
import userSide.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class PlatformTest {

    private Platform platform = Platform.getInstance();
    private User testUser = new User("nomeProva"+randomStringGeneratore(), "cognomeProva"+randomStringGeneratore(), "usernameProva"+randomStringGeneratore(), "emailProva"+randomStringGeneratore()+"@test.com");;
    private String passwordTest="1234";
    private Map<Card,Integer> cardsOwn;
    private Map<Card,Integer> cardsWanted;


    @Test
    public void signupTest(){
        try {
            Boolean result = this.platform.SignUp(testUser.getNome(), testUser.getCognome(), testUser.getUsername(), testUser.getEmail(), passwordTest, "retype");
            cardsOwn = new CollectionOwnDaoImpl().getCollentionOwn(testUser);

            assertEquals(true, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        try {
            String secretkey = "chiavesupersegretissimaXD";
            String pass = Platform.encrypt(passwordTest, secretkey);
            CollectionOwn result = this.platform.LogIn("Obe", pass);
            assert(result instanceof CollectionOwn);
            //TODO to finish

        }catch (Exception e){

        }
    }

    @Test
    public void setExchangeTest(){

        ArrayList<Integer> cardsToGive = setTestCards(1);
        ArrayList<Integer> cardsToTake = setTestCards(5);
        try {
            this.platform.setExchange("Obe", cardsToGive, cardsToTake);
            //TODO cercare un modo per controllare se scambio è settato anche non sapendo l'id_trans

        } catch (Exception e){
            e.printStackTrace();
        }
    }







    private User getTestUser(){
        User tempUser = new User("nomeProva"+randomStringGeneratore(), "cognomeProva"+randomStringGeneratore(), "emailProva"+randomStringGeneratore()+"@test.com", "passwordTest"+randomStringGeneratore());
        return testUser;
    }

    private String randomStringGeneratore(){
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();

            return generatedString;
    }

    /**
     * method to set 5 sorted cards in arraylist
     * @param i
     * @return set List
     */
    private ArrayList<Integer> setTestCards(int i){
        ArrayList<Integer> setList = new ArrayList<>();
        int range = i+5;
        for(; i<range; i++){
            setList.add(i);
        }
        return setList;
    }

    public String getPasswordTest() {
        return passwordTest;
    }

    public void setPasswordTest(String passwordTest) {
        this.passwordTest = passwordTest;
    }
}

