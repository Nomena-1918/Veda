package veda.godao.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QueryUtils {
    public static String[] getColumnNames(Class c) throws Exception{
        Field[] fields=c.getDeclaredFields();
        LinkedList<String> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            if(annote!=null){
                liste.add(annote.annotationType().getMethod(Constantes.COLUMN_VALUE).invoke(annote).toString());
            }
        }
        String[] colonnes=new String[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static String[] getColumnNamesWithoutPrimary(Class c) throws Exception{
        Field[] fields=c.getDeclaredFields();
        LinkedList<String> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            Annotation primary=f.getAnnotation(PrimaryKey.class);
            if(annote!=null&&primary==null){
                liste.add(annote.annotationType().getMethod(Constantes.COLUMN_VALUE).invoke(annote).toString());
            }
        }
        String[] colonnes=new String[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static String[] getNotNullColumnNames(Object o) throws Exception{
        Class c=o.getClass();
        Field[] fields=c.getDeclaredFields();
        LinkedList<String> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            if(annote==null){
                continue;
            }
            f.setAccessible(true);
            Object attr=f.get(o);
            if(attr==null){
                continue;
            }
            String columnName=annote.annotationType().getMethod("value").invoke(annote).toString();
            liste.add(columnName);
            f.setAccessible(false);
        }
        String[] colonnes=new String[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static Field[] getColumnsWithoutPrimary(Class c) throws Exception{
        Field[] fields=c.getDeclaredFields();
        LinkedList<Field> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            Annotation primary=f.getAnnotation(PrimaryKey.class);
            if(annote!=null&&primary==null){
                liste.add(f);
            }
        }
        Field[] colonnes=new Field[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static Field[] getColumns(Class c) throws Exception{
        Field[] fields=c.getDeclaredFields();
        LinkedList<Field> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            Annotation primary=f.getAnnotation(PrimaryKey.class);
            if(annote!=null&&primary==null){
                liste.add(f);
            }
        }
        Field[] colonnes=new Field[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static Field[] getNotNullColumns(Object o) throws Exception{
        Class c=o.getClass();
        Field[] fields=c.getDeclaredFields();
        LinkedList<Field> liste=new LinkedList<>();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            if(annote==null){
                continue;
            }
            f.setAccessible(true);
            Object attr=f.get(o);
            if(attr==null){
                continue;
            }
            liste.add(f);
            f.setAccessible(false);
        }
        Field[] colonnes=new Field[liste.size()];
        for(int i=0;i<colonnes.length;i++){
            colonnes[i]=liste.get(i);
        }
        return colonnes;
    }
    public static HashMap<String, Object> getNotNullColumnValues(Object o) throws Exception{
        HashMap<String, Object> colonnes=new HashMap<>();
        Class c=o.getClass();
        Field[] fields=c.getDeclaredFields();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            if(annote==null){
                continue;
            }
            f.setAccessible(true);
            Object attr=f.get(o);
            if(attr==null){
                continue;
            }
            String columnName=annote.annotationType().getMethod(Constantes.COLUMN_VALUE).invoke(annote).toString();
            colonnes.put(columnName, attr);
            f.setAccessible(false);
        }
        return colonnes;
    }
    public static HashMap<Field, String> getColumnsWithField(Class c) throws Exception{
        HashMap<Field, String> colonnes=new HashMap<>();
        Field[] fields=c.getDeclaredFields();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(Column.class);
            if(annote==null){
                continue;
            }
            String columnName=annote.annotationType().getMethod(Constantes.COLUMN_VALUE).invoke(annote).toString();
            colonnes.put(f, columnName);
        }
        return colonnes;
    }
    public static String getInsertQueryWithoutPrimary(Class c) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        String[] colonnes=getColumnNamesWithoutPrimary(c);
        StringBuilder query= new StringBuilder("insert into " + table + "(");
        for(int i=0;i<colonnes.length;i++){
            if(i==colonnes.length-1){
                query.append(colonnes[i]).append(")");
                break;
            }
            query.append(colonnes[i]).append(", ");
        }
        query.append(" values(");
        for(int i=0;i<colonnes.length;i++){
            if(i==colonnes.length-1){
                query.append("?)");
                break;
            }
            query.append("?, ");
        }
        System.out.println(query);
        return query.toString();
    }
    public static String getInsertQueryWithPrimary(Class c) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        String[] colonnes=getColumnNames(c);
        StringBuilder query= new StringBuilder("insert into " + table + "(");
        for(int i=0;i<colonnes.length;i++){
            if(i==colonnes.length-1){
                query.append(colonnes[i]).append(")");
                break;
            }
            query.append(colonnes[i]).append(", ");
        }
        query.append(" values(");
        for(int i=0;i<colonnes.length;i++){
            if(i==colonnes.length-1){
                query.append("?)");
                break;
            }
            query.append("?, ");
        }
        System.out.println(query);
        return query.toString();
    }
    public static String getSelectQuery(Class c) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        System.out.println("select * from "+table);
        return "select * from "+table;
    }
    public static String getSelectQuery(Class c, Object where) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        String[] columns=getNotNullColumnNames(where);
        StringBuilder query= new StringBuilder("select * from " + table + " where ");
        for(int i=0; i<columns.length; i++){
            if(i==columns.length-1){
                query.append(columns[i]).append(" = ?");
                break;
            }
            query.append(columns[i]).append(" = ? and ");
        }
        System.out.println(query);
        return query.toString();
    }
    public static String getUpdateQuery(Class c, Object change, Object where) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        StringBuilder query= new StringBuilder("update " + table + " set ");
        String[] changeColumns=getNotNullColumnNames(change);
        for(int i=0; i<changeColumns.length; i++){
            if(i==changeColumns.length-1){
                query.append(changeColumns[i]).append(" = ? where ");
                break;
            }
            query.append(changeColumns[i]).append(" = ?, ");
        }
        String[] whereColumns=getNotNullColumnNames(where);
        for(int i=0; i<whereColumns.length; i++){
            if(i==whereColumns.length-1){
                query.append(whereColumns[i]).append(" = ?");
                break;
            }
            query.append(whereColumns[i]).append(" = ? and ");
        }
        System.out.println(query);
        return query.toString();
    }
    public static String getDeleteQuery(Class c, Object where) throws Exception{
        Annotation annote=c.getAnnotation(Table.class);
        String table=annote.annotationType().getMethod(Constantes.TABLE_VALUE).invoke(annote).toString();
        StringBuilder query= new StringBuilder("delete from " + table + " where ");
        String[] whereColumns=getNotNullColumnNames(where);
        for(int i=0; i<whereColumns.length; i++){
            if(i==whereColumns.length-1){
                query.append(whereColumns[i]).append(" = ?");
                break;
            }
            query.append(whereColumns[i]).append(" = ? and ");
        }

        System.out.println(query);

        return query.toString();
    }
    public static Field getPrimaryField(Class c){
        Field[] fields=c.getDeclaredFields();
        for(Field f:fields){
            Annotation annote=f.getAnnotation(PrimaryKey.class);
            if(annote!=null){
                return f;
            }
        }
        return null;
    }
    public static PreparedStatement mapStatement(PreparedStatement statement, Field[] fields, Object o) throws Exception{
        Class c=o.getClass();
        for(int i=0;i<fields.length;i++){
            Annotation annote=fields[i].getAnnotation(ForeignKey.class);
            Class type=fields[i].getType();
            fields[i].setAccessible(true);
            if(annote!=null){
                Field primary=getPrimaryField(type);
                assert primary != null;
                primary.setAccessible(true);
                Object foreignId=primary.get(fields[i].get(o));
                String fieldType=primary.getType().getSimpleName();
                switch (fieldType) {
                    case "Integer" -> statement.setInt(i + 1, (int) foreignId);
                    case "String" -> statement.setString(i + 1, foreignId.toString());
                    case "Double" -> statement.setDouble(i + 1, (double) foreignId);
                    case "LocalDate" -> statement.setDate(i + 1, Date.valueOf((LocalDate) foreignId));
                    case "LocalDateTime" -> statement.setTimestamp(i + 1, Timestamp.valueOf((LocalDateTime) foreignId));
                }
                primary.setAccessible(false);
                continue;
            }
            String fieldType=type.getSimpleName();
            String fieldName=fields[i].getName();
            if(fieldType.equals("Integer")){
                statement.setInt(i+1, (int)c.getMethod("get"+StringUtils.majStart(fieldName)).invoke(o));
            }else if(fieldType.equals("String")){
                statement.setString(i+1, fields[i].get(o).toString());
            }else if(fieldType.equals("Double")){
                statement.setDouble(i+1, (double)c.getMethod("get"+StringUtils.majStart(fieldName)).invoke(o));
            }else if(fieldType.equals("LocalDate")){
                statement.setDate(i+1, Date.valueOf((LocalDate)fields[i].get(o)));
            }else if(fieldType.equals("LocalDateTime")){
                statement.setTimestamp(i+1, Timestamp.valueOf((LocalDateTime)fields[i].get(o)));
            }
        }


        return statement;
    }
    public static Object[] mapStatement(PreparedStatement statement, Field[] fields, Object o, int offset) throws Exception{
        Object[] response=new Object[2];
        PreparedStatement statemnt=statement;
        Class c=o.getClass();
        int upOffset=offset;
        for(int i=offset;i<(fields.length+offset);i++){
            Annotation annote=fields[i-offset].getAnnotation(ForeignKey.class);
            Class type=fields[i-offset].getType();
            fields[i-offset].setAccessible(true);
            if(annote!=null){
                Field primary=getPrimaryField(type);
                primary.setAccessible(true);
                Object foreignId=primary.get(fields[i-offset].get(o));
                String fieldType=primary.getType().getSimpleName();
                switch (fieldType) {
                    case "Integer" -> statemnt.setInt(i + 1, (int) foreignId);
                    case "String" -> statemnt.setString(i + 1, foreignId.toString());
                    case "Double" -> statemnt.setDouble(i + 1, (double) foreignId);
                    case "LocalDate" -> statemnt.setDate(i + 1, Date.valueOf((LocalDate) foreignId));
                    case "LocalDateTime" -> statemnt.setTimestamp(i + 1, Timestamp.valueOf((LocalDateTime) foreignId));
                }
                primary.setAccessible(false);
                continue;
            }
            String fieldType=type.getSimpleName();
            String fieldName=fields[i-offset].getName();
            if(fieldType.equals("Integer")){
                statemnt.setInt(i+1, (int)c.getMethod("get"+StringUtils.majStart(fieldName)).invoke(o));
            }else if(fieldType.equals("String")){
                statemnt.setString(i+1, fields[i-offset].get(o).toString());
            }else if(fieldType.equals("Double")){
                statemnt.setDouble(i+1, (double)c.getMethod("get"+StringUtils.majStart(fieldName)).invoke(o));
            }else if(fieldType.equals("LocalDate")){
                statemnt.setDate(i+1, Date.valueOf((LocalDate)fields[i-offset].get(o)));
            }else if(fieldType.equals("LocalDateTime")){
                statemnt.setTimestamp(i+1, Timestamp.valueOf((LocalDateTime)fields[i-offset].get(o)));
            }
            upOffset++;
        }
        response[0]=statemnt;
        response[1]=upOffset;
        return response;
    }
    public static Object mapResultSet(Connection connex, ResultSet result, Object object, HashMap<Field, String> columns, DAO dao) throws Exception{
        Object obj=object;
        Class c=object.getClass();
        for(Map.Entry<Field, String> entry:columns.entrySet()){
            Field f=entry.getKey();
            f.setAccessible(true);
            Class type=f.getType();
            Annotation annote=f.getAnnotation(ForeignKey.class);
            if(annote!=null){
                boolean recursive=(boolean)annote.annotationType().getMethod("recursive").invoke(annote);
                if(recursive==false){
                    continue;
                }
                Object foreignValue=type.getConstructor().newInstance();
                Field primary=getPrimaryField(type);
                String fieldType=primary.getType().getSimpleName();
                if(fieldType.equals("Integer")){
                    Integer val=Integer.valueOf(result.getInt(entry.getValue()));
                    type.getMethod("set"+StringUtils.majStart(primary.getName()), primary.getType()).invoke(foreignValue, val);
                }else if(fieldType.equals("String")){
                    primary.set(foreignValue, result.getString(entry.getValue()));
                }else if(fieldType.equals("Double")){
                    Double val=Double.valueOf(result.getDouble(entry.getValue()));
                    type.getMethod("set"+StringUtils.majStart(primary.getName()), primary.getType()).invoke(foreignValue, val);
                }else if(fieldType.equals("LocalDate")){
                    primary.set(foreignValue, result.getDate(entry.getValue()).toLocalDate());
                }else if(fieldType.equals("LocalDateTime")){
                    primary.set(foreignValue, result.getTimestamp(entry.getValue()).toLocalDateTime());
                }
                Object objet=dao.select(connex, type, foreignValue)[0];
                f.set(obj, objet);
                continue;
            }
            String fieldType=type.getSimpleName();
            String fieldName=f.getName();
            if(fieldType.equals("Integer")){
                Integer val= result.getInt(entry.getValue());
                c.getMethod("set"+StringUtils.majStart(fieldName), f.getType()).invoke(obj, val);
            }else if(fieldType.equals("String")){
                f.set(obj, result.getString(entry.getValue()));
            }else if(fieldType.equals("Double")){
                Double val= result.getDouble(entry.getValue());
                c.getMethod("set"+StringUtils.majStart(fieldName), f.getType()).invoke(obj, val);
            }else if(fieldType.equals("LocalDate")){
                f.set(obj, result.getDate(entry.getValue()).toLocalDate());
            }else if(fieldType.equals("LocalDateTime")){
                f.set(obj, result.getTimestamp(entry.getValue()).toLocalDateTime());
            }
            f.setAccessible(false);
        }
        return obj;
    }
}
