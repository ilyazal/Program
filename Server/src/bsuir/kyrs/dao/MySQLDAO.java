package bsuir.kyrs.dao;

import bsuir.kyrs.dao.exception.DAOException;

import java.util.List;


public interface MySQLDAO<T, K> {
    List<T> select() throws DAOException;
    T selectByKey(K key) throws DAOException;
    void delete(int instance) throws DAOException;
//    boolean select(String user, int password);
}
