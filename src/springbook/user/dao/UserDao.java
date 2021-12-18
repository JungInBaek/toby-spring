package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    private JdbcContext jdbcContext;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        this.jdbcContext = new JdbcContext();
        this.jdbcContext.setDataSource(dataSource);
    }

    //  추가
    public void add(final User user) throws SQLException {
        jdbcContext.preparedSql("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    //  조회
    public User get(String id) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select * from users where id = ?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            User user = null;
            if(rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            if(user == null) {
                throw new EmptyResultDataAccessException(1);
            }

            return user;
        } catch(SQLException e) {
            throw e;
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch(SQLException e) {
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch(SQLException e) {
                }
            }
        }
    }

    public void deleteAll() throws SQLException {
        jdbcContext.executeSql("delete from users");
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);
        } catch(SQLException e) {
            throw e;
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch(SQLException e) {
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch(SQLException e) {
                }
            }
        }
    }
}
