package bsuir.kyrs.dao.daopr;

import bsuir.kyrs.bean.entity.User;
import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoPr implements MySQLDAO<User, String> {
    private static final UserDaoPr instance = new UserDaoPr();
    private static final String SELECT_BY_LOGIN = "SELECT * FROM Users WHERE username= ?";
//    boolean let_in = false;

    private UserDaoPr(){
    }

    public static MySQLDAO<User, String> getInstance(){
        return instance;
    }

    @Override
    public List<User> select() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User selectByKey(String login) throws DAOException {
        Connection connection = null;
        User user = null;
        try {
            connection = ConnectionPool.getInstance().getFreeConnection();

            try(PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN)) {
                ps.setString(1, login);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        user = new User();
                        user.setId(rs.getInt(1));
                        user.setUsername(rs.getString(2));
                        user.setPassword(rs.getInt(3));
                        user.setAdmin(rs.getBoolean(4));
                        return user;
                    }
                }
            }
        } catch(SQLException e){
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().dispose(connection);
        }
        return user;
    }


    @Override
    public void delete(int user) throws DAOException {
        throw new UnsupportedOperationException();
    }
}
