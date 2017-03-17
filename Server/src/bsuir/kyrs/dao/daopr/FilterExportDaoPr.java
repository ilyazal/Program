package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.connection.ConnectionPool;
import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FilterExportDaoPr implements AccountingDAO<Export,String>{
    private static final String SELECT_FILTER_EXPORTS="SELECT * FROM exports WHERE factory=?";
    private static final FilterExportDaoPr instance = new FilterExportDaoPr();
    public FilterExportDaoPr() {

    }

    public static AccountingDAO<Export, String> getInstance(){
        return instance;
    }
    @Override
    public List<Export> select() throws DAOException {
        throw new UnsupportedMediaException();
    }

    @Override
    public Export selectByKey(String key) throws DAOException {
        throw new UnsupportedMediaException();
    }


    @Override
    public void delete(int instance) throws DAOException {
        throw new UnsupportedMediaException();
    }



    @Override
    public List<Export> selectByFactory(String fac) throws DAOException {
        Connection connection = null;
        List<Export> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();
            try(PreparedStatement ps = connection.prepareStatement(SELECT_FILTER_EXPORTS)) {
                ps.setString(1, fac);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        list.add(setExport(rs));
                    }
                }
            }
        } catch(SQLException e){
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().dispose(connection);
        }
        return list;
    }

    @Override
    public void insertImport(String called, String material, int quantity, LocalDate date, String factory, int monye) throws DAOException {

    }

    @Override
    public void insertExport(String called, String material, int quantity, LocalDate date, String factory, int monye) throws DAOException {
        throw new UnsupportedOperationException();
    }

    private Export setExport(ResultSet rs) throws SQLException {
        Export usInf = new Export();
        usInf.setId(rs.getInt(1));
        usInf.setCalled(rs.getString(2));
        usInf.setMaterial(rs.getString(3));
        usInf.setQuantity(rs.getInt(4));
        usInf.setDate2(rs.getDate(5));
        usInf.setFactory(rs.getString(6));
        usInf.setSum(rs.getInt(7));
        return usInf;
    }
    }




