package veda.godao;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import veda.godao.exceptions.ConnectionException;
import veda.godao.utils.Constantes;
import veda.godao.utils.DAOConnexion;
import veda.godao.utils.QueryUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class DAO {
    private String database;
    private String host;
    private String port;
    private String user, pwd;
    private boolean useSSL;
    private int sgbd;
    
    public DAO() {
    }
    public DAO(String database, String host, String port, String user, String pwd, boolean useSSL, int sgbd) {
        this.sgbd = sgbd;
        this.database = database;
        this.host = host;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
        this.useSSL = useSSL;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public boolean isUseSSL() {
        return useSSL;
    }
    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }
    public int getSgbd() {
        return sgbd;
    }
    public void setSgbd(int sgbd) {
        this.sgbd = sgbd;
    }
    public void insertWithoutPrimaryKey(Connection connex, Object o) throws Exception{
        Class c=o.getClass();
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            switch(getSgbd()){
                case Constantes.MYSQL_ID:
                    connect=DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                case Constantes.PSQL_ID:
                    connect=DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                default:
                    throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            }
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getInsertQueryWithoutPrimary(c));
        Field[] fields=QueryUtils.getColumnsWithoutPrimary(c);
        statemnt=QueryUtils.mapStatement(statemnt, fields, o);
        try{
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            if(opened){
                connect.close();
            }
            statemnt.close();
        }
    }
    public void insertWithPrimaryKey(Connection connex, Object o) throws Exception{
        Class c=o.getClass();
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            switch(getSgbd()){
                case Constantes.MYSQL_ID:
                    connect=DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                case Constantes.PSQL_ID:
                    connect=DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                default:
                    throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            }
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getInsertQueryWithPrimary(c));
        Field[] fields=QueryUtils.getColumns(c);
        statemnt=QueryUtils.mapStatement(statemnt, fields, o);
        try{
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            if(opened){
                connect.close();
            }
            statemnt.close();
        }
    }
    public <T>T[] select(Connection connex, Class<T> c) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            connect = switch (getSgbd()) {
                case Constantes.MYSQL_ID ->
                        DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                case Constantes.PSQL_ID ->
                        DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                default -> throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            };
            opened=true;
        }
        try (PreparedStatement statemnt = connect.prepareStatement(QueryUtils.getSelectQuery(c))) {
            HashMap<Field, String> columns = QueryUtils.getColumnsWithField(c);
            LinkedList liste = new LinkedList();
            ResultSet result = statemnt.executeQuery();
            while (result.next()) {
                T obj = (T) c.getConstructor().newInstance();
                obj = (T) QueryUtils.mapResultSet(connect, result, obj, columns, this);
                liste.add(obj);
            }
            T[] objets = (T[]) Array.newInstance(c, liste.size());
            for (int i = 0; i < objets.length; i++) {
                objets[i] = (T) liste.get(i);
            }
            return objets;
        } finally {
            if (opened) {
                connect.close();
            }
        }
    }
    public <T>T[] select(Connection connex, Class<T> c, T where) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            connect = switch (getSgbd()) {
                case Constantes.MYSQL_ID ->
                        DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                case Constantes.PSQL_ID ->
                        DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                default -> throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            };
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getSelectQuery(c, where));
        Field[] fields=QueryUtils.getNotNullColumns(where);
        statemnt=QueryUtils.mapStatement(statemnt, fields, where);
        HashMap<Field, String> columns=QueryUtils.getColumnsWithField(c);
        try{
            LinkedList liste=new LinkedList();
            ResultSet result=statemnt.executeQuery();
            while(result.next()){
                T obj=c.getConstructor().newInstance();
                obj=(T)QueryUtils.mapResultSet(connect, result, obj, columns, this);
                liste.add(obj);
            }
            T[] objets=(T[])Array.newInstance(c, liste.size());
            for(int i=0;i<objets.length;i++){
                objets[i]=(T)liste.get(i);
            }
            return objets;
        }finally{
            if(opened){
                connect.close();
            }
            statemnt.close();
        }
    }
    public void update(Connection connex, Object change, Object where) throws Exception{
        Class c=change.getClass();
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            switch(getSgbd()){
                case Constantes.MYSQL_ID:
                    connect=DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                case Constantes.PSQL_ID:
                    connect=DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                default:
                    throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            }
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getUpdateQuery(c, change, where));
        Field[] changeFields=QueryUtils.getNotNullColumns(change);
        Field[] whereFields=QueryUtils.getNotNullColumns(where);
        int offset=0;
        Object[] mapping=QueryUtils.mapStatement(statemnt, changeFields, change, offset);
        statemnt=(PreparedStatement)mapping[0];
        offset=(int)mapping[1];
        mapping=QueryUtils.mapStatement(statemnt, whereFields, where, offset);
        statemnt=(PreparedStatement)mapping[0];
        try{
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            if(opened){
                connect.close();
            }
            statemnt.close();
        }
    }
    public void delete(Connection connex, Object where) throws Exception{
        Class c=where.getClass();
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            switch(getSgbd()){
                case Constantes.MYSQL_ID:
                    connect=DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                case Constantes.PSQL_ID:
                    connect=DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                    break;
                default:
                    throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            }
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getDeleteQuery(c, where));
        Field[] whereFields=QueryUtils.getNotNullColumns(where);
        statemnt=QueryUtils.mapStatement(statemnt, whereFields, where);
        try{
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            if(opened){
                connect.close();
            }
            statemnt.close();
        }
    }
    public void customUpdate(Connection connex, String query) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connect==null){
            connect = switch (getSgbd()) {
                case Constantes.MYSQL_ID ->
                        DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                case Constantes.PSQL_ID ->
                        DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                default -> throw new ConnectionException(Constantes.NOCONNECTION_CODE);
            };
            opened=true;
        }
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.executeUpdate();
            if (opened) {
                connect.commit();
            }
        } catch (Exception e) {
            connect.rollback();
            throw e;
        } finally {
            if (opened) {
                connect.close();
            }
        }
    }
}
