package fr.epsi.lortet.androidci;

import java.util.Map;

public class Users {

    private Integer id;
    private String name;

    Users() {
        id = null;
        name = null;
    }

    public void login(String username, String password) throws IncorrectLoginException {

        Map<String,String> env = System.getenv();

        if(env.get("platform").equals("dev")) {
            if (!username.equals("dev") || !password.equals("dev_pass")) {
                throw new IncorrectLoginException("Bad username/password for platform '" + env.get("platform") + "'");
            }
            id = 1;
            name = username;
            return;
        }


        String dbUrl;
        int dbPort;
        if(env.get("platform") != null && env.get("platform").equals("circleci")) {
            dbUrl = env.get("DB_URL");
            dbPort = Integer.parseInt(env.get("DB_PORT"));
        } else {
            dbUrl = BuildConfig.DB_URL;
            dbPort = BuildConfig.DB_PORT;
        }

        throw new IncorrectLoginException("db url " + dbUrl + " dbPort " + dbPort);
    }
}
