package veda.base;
import java.sql.PreparedStatement;

import veda.chrono.DateGen;
import veda.help.StringHelper;
import veda.reflect.Datas;
import veda.reflect.Reflect;

public class Query{
    static String prepareParenthesis(int nbNotNullFields){
        String[] tokens=new String[nbNotNullFields];
        for(int i=0; i<nbNotNullFields; i++){
            tokens[i]="?";
        }
        String response=StringHelper.listParenthesis(tokens);
        return response;
    }
    static String prepareAndList(String[] notNullFields){
        String response=notNullFields[0]+"=?";
        for(int i=1; i<notNullFields.length; i++){
            response+=" and "+notNullFields[i]+"=?";
        }
        return response;
    }
    static String prepareSetListUpdate(String[] notNullFields, String primaryField){
        int etape=0;
        String response="";
        for(int i=0; i<notNullFields.length; i++){
            if(notNullFields[i].equals(primaryField)){
                continue;
            }
            if(etape==0){
                response+=notNullFields[i]+"=?";
            }else{
                response+=", "+notNullFields[i]+"=?";
            }
            etape++;
        }
        return response;
    }
    static String prepareSetListUpdate(String[] notNullFields, String[] primaryField){
        int etape=0;
        String response="";
        for(int i=0; i<notNullFields.length; i++){
            boolean isPrimary=false;
            for(int j=0; j<primaryField.length; j++){
                if(notNullFields[i].equals(primaryField[j])){
                    isPrimary=true;
                    break;
                }
            }
            if(isPrimary){
                continue;
            }
            if(etape==0){
                response+=notNullFields[i]+"=?";
            }else{
                response+=", "+notNullFields[i]+"=?";
            }
            etape++;
        }
        return response;
    }
    private static void setAttribute(PreparedStatement statemnt, int index, Object value, String type) throws Exception{
        if(type.equals("Integer")){
            statemnt.setInt(index, (Integer)value);
        }else if(type.equals("String")){
            statemnt.setString(index, String.valueOf(value));
        }else if(type.equals("DateGen")){
            statemnt.setDate(index, ((DateGen)value).toDateOnly().toDateSql());
        }
    }
    static void setObject(PreparedStatement statemnt, Object object) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        String[] fieldNames=reflect.getNotNullFieldsName(object);
        String[] fieldTypes=reflect.getNotNullFieldsType(object);
        for(int i=0; i<fieldNames.length; i++){
            setAttribute(statemnt, i+1, Reflect.get(object, fieldNames[i]), Datas.revertType(fieldTypes[i]));
        }
    }
    private static int setUpdateObject(PreparedStatement statemnt, Object object, String primaryField) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        String[] fieldNames=reflect.getNotNullFieldsName(object);
        String[] fieldTypes=reflect.getNotNullFieldsType(object);
        int indice=1;
        for(int i=0; i<fieldNames.length; i++){
            if(fieldNames[i].equals(primaryField)){
                continue;
            }
            setAttribute(statemnt, indice, Reflect.get(object, fieldNames[i]), Datas.revertType(fieldTypes[i]));
            indice++;
        }
        return indice;
    }
    private static int setUpdateObject(PreparedStatement statemnt, Object object, String[] primaryField) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        String[] fieldNames=reflect.getNotNullFieldsName(object);
        String[] fieldTypes=reflect.getNotNullFieldsType(object);
        int indice=1;
        for(int i=0; i<fieldNames.length; i++){
            boolean isPrimary=false;
            for(int j=0; j<primaryField.length; j++){
                if(fieldNames[i].equals(primaryField[j])){
                    isPrimary=true;
                    break;
                }
            }
            if(isPrimary){
                continue;
            }
            setAttribute(statemnt, indice, Reflect.get(object, fieldNames[i]), Datas.revertType(fieldTypes[i]));
            indice++;
        }
        return indice;
    }
    public static void setUpdate(PreparedStatement statemnt, Object object, String primaryField) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        int lastIndex=setUpdateObject(statemnt, object, primaryField);
        String type=reflect.typeOfField(object, primaryField);
        setAttribute(statemnt, lastIndex, Reflect.get(object, primaryField), Datas.revertType(type));
    }
    public static void setUpdate(PreparedStatement statemnt, Object object, String[] primaryField) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        int lastIndex=setUpdateObject(statemnt, object, primaryField);
        for(int i=0; i<primaryField.length; i++){
            String type=reflect.typeOfField(object, primaryField[i]);
            setAttribute(statemnt, lastIndex, Reflect.get(object, primaryField[i]), Datas.revertType(type));
            lastIndex++;
        }
    }
}
