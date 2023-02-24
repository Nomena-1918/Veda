package veda.block;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.LinkedList;

import veda.chrono.DateGen;
import veda.reflect.Reflect;

public class Vector<E> extends LinkedList<E>{
    public void convertToList(E[] array){
        for(E element:array){
            add(element);
        }
    }
    @SuppressWarnings(value = { "unchecked" })
    public void toList(ResultSet results, E object) throws Exception{
        Reflect reflect=new Reflect(object.getClass());
        String[] fieldName=reflect.getFieldNames();
        String[] fieldType=reflect.getFieldTypes();
        while(results.next()){
            E obj=(E)reflect.construct(object);
            for(int i=0; i<fieldName.length; i++){
                Object value=results.getObject(fieldName[i]);
                if(fieldType[i].equals("DateGen")){
                    value=new DateGen((Date)value);
                }
                Reflect.set(obj, fieldName[i], fieldType[i], value);
            }
            add(obj);
        }
    }
}
