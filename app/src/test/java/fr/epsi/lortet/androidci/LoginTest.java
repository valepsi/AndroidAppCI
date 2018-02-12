package fr.epsi.lortet.androidci;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void isConnectionGranted() {

        Map<String, String> env = System.getenv();

        Users user = new Users();

        try {
            user.login(env.get("test_login"), env.get("test_password"));
        } catch(IncorrectLoginException e) {
            fail(e.getMessage());
        }
    }
}
