package veda.godao;

import veda.godao.entity.EntityTable;

import java.sql.Connection;
import java.util.HashMap;

public interface DAOInterface {
    int insertWithoutPrimaryKey(Connection connex, Object o) throws Exception;

    void insertWithPrimaryKey(Connection connex, Object o) throws Exception;

    <T> T[] select(Connection connex, Class<T> c) throws Exception;

    <T> T[] select(Connection connex, Class<T> c, int limit, int offset) throws Exception;

    <T> T[] select(Connection connex, Class<T> c, T where) throws Exception;

    Object[] select_object(Connection connex, Class<?> c, Object where) throws Exception;

    void update(Connection connex, Object change, Object where) throws Exception;

    void delete(Connection connex, Object where) throws Exception;

    void customUpdate(Connection connex, String query) throws Exception;

    int count(Connection connex, Class<?> c) throws Exception;

    HashMap<String, Object>[] select(Connection connex, String query) throws Exception;

    void execute(Connection connex, String query) throws Exception;

    boolean exists(Connection connex, String tablename, String... conditions) throws Exception;

    <T> boolean exists(Connection connex, Class<T> c, T where) throws Exception;

    void createTable(Connection connex, EntityTable table, boolean temporary) throws Exception;
}
