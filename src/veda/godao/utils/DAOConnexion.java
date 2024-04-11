package veda.godao.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import veda.godao.DAO;

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
    public static Connection getConnexion(String driver, String sgbd, String host, String port, String database, String user, String pwd, boolean useSSL, boolean allowPublicKeyRetrieval) throws Exception{
        Class.forName(driver);
        String url=String.format("jdbc:%s://%s:%s/%s?user=%s&password=%s&useSSL=%s&allowPublicKeyRetrieval=%s", sgbd, host, port, database, user, pwd, useSSL, allowPublicKeyRetrieval);
        Connection conn=DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
    public static Connection getConnexion(DAO dao) throws Exception{
        Class.forName(dao.getDriver());
        String url=String.format("jdbc:%s://%s:%s/%s?user=%s&password=%s&useSSL=%s&allowPublicKeyRetrieval=%s", dao.getServer(), dao.getHost(), dao.getPort(), dao.getDatabase(), dao.getUser(), dao.getPwd(), dao.isUseSSL(), dao.isAllowKeyRetrieval());
        Connection conn=DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        return conn;
    }
}
