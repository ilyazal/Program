package bsuir.kyrs.dao;

import bsuir.kyrs.dao.exception.DAOException;

import java.time.LocalDate;
import java.util.List;


public interface AccountingDAO<T, K> extends MySQLDAO<T, K> {
    List<T> selectByFactory(String fac) throws DAOException;
    void insertImport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException;
    void insertExport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException;
}
