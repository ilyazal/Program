package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ImportAddPr implements AccountingDAO<Import,String> {
    private static final String INSERT_IMPORTS="INSERT INTO imports " +
            "(called, material, quantity, date2,factory, sumimports) " +
            "VALUES(?, ?, ?, ?,?,?)";
    private static final ImportAddPr instance = new ImportAddPr();


    public ImportAddPr() {
    }

    public static AccountingDAO<Import, String> getInstance(){
        return instance;
    }
    @Override

    public List<Import> select() throws DAOException {
        return null;
    }

    @Override
    public Import selectByKey(String key) throws DAOException {
        return null;
    }


    @Override
    public void delete(int instance) throws DAOException {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<Import> selectByFactory(String fac) throws DAOException {
        return null;
    }

    @Override
    public void insertImport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {
        java.sql.Connection connection = null;
        List<Export> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();
            try(PreparedStatement ps = connection.prepareStatement(INSERT_IMPORTS)) {
                ps.setString(1,called);
                ps.setString(2,material);
                ps.setInt(3,quantity);
                ps.setDate(4, Date.valueOf(date2));
                ps.setString(5,factory);
                ps.setInt(6,monye);
                ps.executeUpdate();
            }
        } catch(SQLException e){
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().dispose(connection);
//        }
    }


}

    @Override
    public void insertExport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {

    }


}
