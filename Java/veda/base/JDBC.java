package veda.base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import veda.block.Vector;
import veda.help.StringHelper;
import veda.reflect.Reflect;

public class JDBC<E> {
    private String table;
    private E object;
    public E getObject() {
        return object;
    }
    public void setObject(E object) {
        this.object = object;
    }
    public String getTable() {
        return table;
    }
    public void setTable(String table) {
        this.table = table;
    }
    public String getInsertQuery() throws Exception{
        Reflect reflect=new Reflect(getObject().getClass());
        String[] notNullFields=reflect.getNotNullFieldsName(getObject());
        String query="insert into "+getTable()+StringHelper.listParenthesis(notNullFields);
        query+=" values"+Query.prepareParenthesis(notNullFields.length);
        return query;
    }
    public String getSelectAllQuery(){
        String query="select * from "+getTable();
        return query;
    }
    public String getSelectQuery() throws Exception{
        Reflect reflect=new Reflect(getObject().getClass());
        String[] fieldName=reflect.getNotNullFieldsName(getObject());
        String query="select * from "+getTable()+" where "+Query.prepareAndList(fieldName);
        return query;
    }
    public String getUpdateQuery(String primaryField) throws Exception{
        Reflect reflect=new Reflect(getObject().getClass());
        String[] fieldName=reflect.getNotNullFieldsName(getObject());
        String query="update "+getTable()+" set "+Query.prepareSetListUpdate(fieldName, primaryField)+" where "+primaryField+"=?";
        return query;
    }
    public String getUpdateQuery(String[] primaryField) throws Exception{
        Reflect reflect=new Reflect(getObject().getClass());
        String[] fieldName=reflect.getNotNullFieldsName(getObject());
        String query="update "+getTable()+" set "+Query.prepareSetListUpdate(fieldName, primaryField)+" where "+Query.prepareAndList(primaryField);
        return query;
    }
    public String getDeleteAllQuery(){
        String query="delete from "+getTable();
        return query;
    }
    public String getDeleteQuery() throws Exception{
        Reflect reflect=new Reflect(getObject().getClass());
        String[] fieldName=reflect.getNotNullFieldsName(getObject());
        String query="delete from "+getTable()+" where "+Query.prepareAndList(fieldName);
        return query;
    }
    public void inserer(Connection connex) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getInsertQuery());
        try{
            Query.setObject(statemnt, getObject());
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception e){
            connect.rollback();
            throw e;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public Vector<E> selectAll(Connection connex) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getSelectAllQuery());
        try{
            Vector<E> list=new Vector<>();
            ResultSet result=statemnt.executeQuery();
            list.toList(result, getObject());
            return list;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public Vector<E> select(Connection connex) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getSelectQuery());
        try{
            Vector<E> list=new Vector<>();
            Query.setObject(statemnt, getObject());
            ResultSet result=statemnt.executeQuery();
            list.toList(result, getObject());
            return list;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public void update(Connection connex, String primary) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getUpdateQuery(primary));
        try{
            Query.setUpdate(statemnt, getObject(), primary);
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception e){
            connect.rollback();
            throw e;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public void update(Connection connex, String[] primary) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getUpdateQuery(primary));
        try{
            Query.setUpdate(statemnt, getObject(), primary);
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception e){
            connect.rollback();
            throw e;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public void deleteAll(Connection connex) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getDeleteAllQuery());
        try{
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception e){
            connect.rollback();
            throw e;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
    public void delete(Connection connex) throws Exception{
        boolean opened=false;
        Connection connect=connex;
        if(connex==null){
            connect=new Connect("localhost", "data2", "eriq66", "root").getPostgre();
            opened=true;
        }
        PreparedStatement statemnt=connect.prepareStatement(getDeleteQuery());
        try{
            Query.setObject(statemnt, getObject());
            statemnt.executeUpdate();
            if(opened){
                connect.commit();
            }
        }catch(Exception e){
            connect.rollback();
            throw e;
        }finally{
            statemnt.close();
            if(opened){
                connect.close();
            }
        }
    }
}
