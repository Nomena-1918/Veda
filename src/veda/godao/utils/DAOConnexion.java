package veda.godao.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOConnexion {
    public static Connection getConnexionPostgreSql(String database, String user, String pwd, String host) throws Exception{
        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s/%s?user=%s&password=%s", host, database, user, pwd);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
}
