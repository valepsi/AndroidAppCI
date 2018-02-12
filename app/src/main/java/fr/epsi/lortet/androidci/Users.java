package fr.epsi.lortet.androidci;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        if(env.get("platform") != null && "dev".equals(env.get("platform"))) {
            if (!"dev".equals(username) || !"dev_pass".equals(password)) {
                throw new IncorrectLoginException("Bad username/password for platform '" + env.get("platform") + "'");
            }
            id = 1;
            name = username;
            return;
        }


        String dbUrl;
        int dbPort;
        String dbUsername;
        String dbPassword;
        if(env.get("platform") != null && env.get("platform").equals("circleci")) {
            dbUrl = env.get("DB_URL");
            dbPort = Integer.parseInt(env.get("DB_PORT"));
            dbUsername = env.get("DB_USERNAME");
            dbPassword = env.get("DB_PASSWORD");
        } else {
            dbUrl = BuildConfig.DB_URL;
            dbPort = BuildConfig.DB_PORT;
            dbUsername = BuildConfig.DB_USERNAME;
            dbPassword = BuildConfig.DB_PASSWORD;
        }

        final String JDBC_DRIVER = "org.postgresql.Driver";
        final String DB_URL = "jdbc:postgresql://" + dbUrl + ":" + dbPort + "/postgres";

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver O.K.");
            try {
                Connection conn = DriverManager.getConnection(DB_URL, dbUsername, dbPassword);
                System.out.println("Connection O.K.");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Users WHERE NAME = " + username + "AND PASS = " + password);
                if(rs.next()) {
                    return;
                }
                rs.close();
                st.close();

                throw new IncorrectLoginException("User not found for " + username + " pass " + password);

            } catch (SQLException e) {

                throw new IncorrectLoginException("Connection error => " + e.getMessage());
            }

        } catch (ClassNotFoundException e) {

            throw new IncorrectLoginException("Unknow drive => " + e.getMessage());
        }
    }
}
