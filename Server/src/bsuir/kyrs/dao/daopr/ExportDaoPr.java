package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.Export;
import com.sun.xml.internal.ws.server.UnsupportedMediaException;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ExportDaoPr implements MySQLDAO<Export,String> {
    private static final String SELECT_ONLY_EXPORTS="SELECT * FROM exports";
    private static final ExportDaoPr instance = new ExportDaoPr();
    public ExportDaoPr() {

    }


    public static MySQLDAO<Export, String> getInstance(){
        return instance;
    }

    @Override
    public List<Export> select() throws DAOException {
        java.sql.Connection connection = null;
        List<Export> list = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();

            try(PreparedStatement ps = connection.prepareStatement(SELECT_ONLY_EXPORTS)) {

                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
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
    public Export selectByKey(String key) throws DAOException {
        throw new UnsupportedMediaException();
    }



    @Override
    public void delete(int instance) throws DAOException {
        throw new UnsupportedMediaException();
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
