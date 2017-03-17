package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Import;
import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FilterImportDaoPr implements AccountingDAO<Import,String> {

    private static final String SELECT_FILTER_IMPORTS="SELECT * FROM imports WHERE factory=?";
    private static final FilterImportDaoPr instance = new FilterImportDaoPr();
    public FilterImportDaoPr() {

    }


    public static AccountingDAO<Import, String> getInstance(){
        return instance;
    }




    @Override
    public List<Import> selectByFactory(String fac) throws DAOException {
        java.sql.Connection connection = null;
        List<Import> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();
            try(PreparedStatement ps = connection.prepareStatement(SELECT_FILTER_IMPORTS)) {
                ps.setString(1, fac);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        list.add(setImport(rs));
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

    }



    @Override
    public List<Import> select() throws DAOException {
        throw new UnsupportedMediaException();
    }

    @Override
    public Import selectByKey(String key) throws DAOException {
        throw new UnsupportedMediaException();
    }


    @Override
    public void delete(int instance) throws DAOException {
        throw new UnsupportedMediaException();
    }

    private Import setImport(ResultSet rs) throws SQLException {
        Import usInf = new Import();
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
