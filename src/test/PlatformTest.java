package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import platform.Platform;
import userSide.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class PlatformTest {
    private Platform platform = Platform.getInstance();
    private User user = new User("nomeProva", "cognomeProva", "usernameProva", "mailProva");
    private User testUser = getTestUser();
    private ArrayList<Integer> CardOwn;
    private ArrayList<Integer> CardWanted;

    @BeforeAll
    public void signupTest(){
        try {
            Boolean result = this.platform.SignUp(testUser.getNome(), testUser.getCognome(), testUser.getEmail(),  testUser.getEmail(), testUser.getPass(), "retype");
            assertEquals(true, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest(){
        try {

        }catch (Exception e){

        }
    }







    private User getTestUser(){
        this.testUser.setNome("nomeProva"+randomStringGeneratore());
        this.testUser.setCognome("cognomeProva"+randomStringGeneratore());
        this.testUser.setUsername("usernameProva"+randomStringGeneratore());
        this.testUser.setEmail("emailProva"+randomStringGeneratore()+"@test.com");
        this.testUser.setPass("passwordTest"+randomStringGeneratore());
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
}

