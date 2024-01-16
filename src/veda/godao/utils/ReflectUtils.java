package veda.godao.utils;

import java.lang.reflect.Method;
import java.sql.Connection;

import veda.godao.DAO;

public class ReflectUtils {
    public static Method[] getDAOMethods() throws NoSuchMethodException, SecurityException{
        Method insert=DAO.class.getMethod("insertWithoutPrimaryKey", Connection.class, Object.class);
        Method select=DAO.class.getMethod("select", Connection.class, Class.class);
        Method selectWhere=DAO.class.getMethod("select", Connection.class, Class.class, Object.class);
        Method update=DAO.class.getMethod("update", Connection.class, Object.class, Object.class);
        Method delete=DAO.class.getMethod("delete", Connection.class, Object.class);
        Method[] methods={insert, select, selectWhere, update, delete};
        return methods;
    }
}
