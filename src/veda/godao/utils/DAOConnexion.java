package veda.godao.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAOConnexion {
    public static Connection getConnexionPostgreSql(String database, String user, String pwd, String host, boolean useSSL) throws Exception{
        Class.forName("org.postgresql.Driver");
        String url = String.format("jdbc:postgresql://%s/%s?user=%s&password=%s&useSSL=%s", host, database, user, pwd, useSSL);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
    public static Connection getConnexionMySql(String database, String user, String pwd, String host, boolean useSSL) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useSSL=%s", host, database, user, pwd, useSSL);
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
}
