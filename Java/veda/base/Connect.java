package veda.base;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    private String IP;
    private String database;
    private String user;
    private String password;
    public Connect(String IP, String database, String user, String password){
        setIP(IP);
        setDatabase(database);
        setUser(user);
        setPassword(password);
    }
    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Connection getPostgre() throws Exception{
        Class.forName("org.postgresql.Driver");
        Connection postgre=DriverManager.getConnection("jdbc:postgresql://"+IP+"/"+database, user, password);
        postgre.setAutoCommit(false);
        return postgre;
    }
}
