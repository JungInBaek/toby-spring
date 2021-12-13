package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    //  필드
    private DataSource dataSource;

    //  Setter
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //  getter
    public DataSource getDataSource() {
        return this.dataSource;
    }

    //  추가
    public void add(User user) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch(SQLException e) {
            throw e;
        } finally {
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
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch(SQLException e) {
            throw e;
        } finally {
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
