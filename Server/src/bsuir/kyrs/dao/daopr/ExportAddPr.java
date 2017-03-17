package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ExportAddPr implements AccountingDAO<Export,String> {
    private static final String INSERT_EXPORT="INSERT INTO exports " +
            "(called, material, quantity, date2,factory, sumexports) " +
            "VALUES(?, ?, ?, ?,?,?)";
    private static final ExportAddPr instance = new ExportAddPr();


    public static AccountingDAO<Export, String> getInstance(){
        return instance;
    }

    public ExportAddPr() {
    }

    @Override
    public List<Export> select() throws DAOException {
        return null;
    }

    @Override
    public Export selectByKey(String key) throws DAOException {
        return null;
    }


    @Override
    public void delete(int instance) throws DAOException {

    }


    @Override
    public List<Export> selectByFactory(String fac) throws DAOException {
        return null;
    }

    @Override
    public void insertImport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {

    }

    @Override
    public void insertExport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {
        java.sql.Connection connection = null;
        List<Export> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();
            try(PreparedStatement ps = connection.prepareStatement(INSERT_EXPORT)) {
                ps.setString(1,called);
                ps.setString(2,material);
                ps.setInt(3,quantity);
                ps.setDate(4, Date.valueOf(date2));
                ps.setString(5,factory);
                ps.setInt(6,monye);
                ps.executeUpdate();
                //list.add(setExport(rs));
            }
        } catch(SQLException e){
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().dispose(connection);
        }
    }


}
