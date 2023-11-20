package veda.godao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import veda.godao.exceptions.ConnectionException;
import veda.godao.utils.Constantes;
import veda.godao.utils.QueryUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class DAO {
    public static void insertWithoutPrimaryKey(Connection connex, Object o) throws Exception{
        Class c=o.getClass();
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getInsertQueryWithoutPrimary(c));
        Field[] fields=QueryUtils.getColumnsWithoutPrimary(c);
        statemnt=QueryUtils.mapStatement(statemnt, fields, o);
        try{
            statemnt.executeUpdate();
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            statemnt.close();
        }
    }
    public static void insertWithPrimaryKey(Connection connex, Object o) throws Exception{
        Class c=o.getClass();
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getInsertQueryWithPrimary(c));
        Field[] fields=QueryUtils.getColumns(c);
        statemnt=QueryUtils.mapStatement(statemnt, fields, o);
        try{
            statemnt.executeUpdate();
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            statemnt.close();
        }
    }
    public static Object[] select(Connection connex, Class c) throws Exception{
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getSelectQuery(c));
        HashMap<Field, String> columns=QueryUtils.getColumnsWithField(c);
        try{
            LinkedList liste=new LinkedList();
            ResultSet result=statemnt.executeQuery();
            while(result.next()){
                Object obj=c.getConstructor().newInstance();
                obj=QueryUtils.mapResultSet(connect, result, obj, columns);
                liste.add(obj);
            }
            Object[] objects=new Object[liste.size()];
            for(int i=0;i<objects.length;i++){
                objects[i]=liste.get(i);
            }
            return objects;
        }finally{
            statemnt.close();
        }
    }
    public static Object[] select(Connection connex, Class c, Object where) throws Exception{
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getSelectQuery(c, where));
        Field[] fields=QueryUtils.getNotNullColumns(where);
        statemnt=QueryUtils.mapStatement(statemnt, fields, where);
        HashMap<Field, String> columns=QueryUtils.getColumnsWithField(c);
        try{
            LinkedList liste=new LinkedList();
            ResultSet result=statemnt.executeQuery();
            while(result.next()){
                Object obj=c.getConstructor().newInstance();
                obj=QueryUtils.mapResultSet(connect, result, obj, columns);
                liste.add(obj);
            }
            Object[] objects=new Object[liste.size()];
            for(int i=0;i<objects.length;i++){
                objects[i]=liste.get(i);
            }
            return objects;
        }finally{
            statemnt.close();
        }
    }
    public static void update(Connection connex, Object change, Object where) throws Exception{
        Class c=change.getClass();
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
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
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            statemnt.close();
        }
    }
    public static void delete(Connection connex, Object where) throws Exception{
        Class c=where.getClass();
        Connection connect=connex;
        if(connect==null){
            throw new ConnectionException(Constantes.NOCONNECTION_CODE);
        }
        PreparedStatement statemnt=connect.prepareStatement(QueryUtils.getDeleteQuery(c, where));
        Field[] whereFields=QueryUtils.getNotNullColumns(where);
        statemnt=QueryUtils.mapStatement(statemnt, whereFields, where);
        try{
            statemnt.executeUpdate();
        }catch(Exception ex){
            connect.rollback();
            throw ex;
        }finally{
            statemnt.close();
        }
    }
}
