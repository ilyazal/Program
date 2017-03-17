package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Import;
import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ImportDelDaoPr implements AccountingDAO<Import,String> {
    private static final String DELETE_IMPORTS="DELETE FROM imports WHERE id=?";
    private static final ImportDelDaoPr instance = new ImportDelDaoPr();
    public ImportDelDaoPr() {
    }

    public static MySQLDAO<Import, String> getInstance(){
        return instance;
    }


    @Override
    public List select() throws DAOException {
        throw  new UnsupportedMediaException();
    }

    @Override
    public Import selectByKey(String key) throws DAOException {
        return null;
    }


    @Override
    public void delete(int instance) throws DAOException {
        java.sql.Connection connection = null;
        List<Import> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();
            try(PreparedStatement ps = connection.prepareStatement(DELETE_IMPORTS)) {
                ps.setInt(1,instance);
                ps.executeUpdate();
                //list.add(setExport(rs));
            }
        } catch(SQLException e){
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().dispose(connection);
        }
    }



    @Override
    public List<Import> selectByFactory(String fac) throws DAOException {
        return null;
    }

    @Override
    public void insertImport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {

    }

    @Override
    public void insertExport(String called, String material, int quantity, LocalDate date2, String factory, int monye) throws DAOException {

    }



}
