package springbook.user.dao;

import org.h2.api.ErrorCode;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy strategy) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = strategy.makePreparedStatement(c);
            ps.executeUpdate();
        } catch(SQLException e) {
            if(e.getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                throw new DuplicateUserIdException(e);
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            if(ps != null) {
                try {
                    ps.close();
                } catch(SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(c != null) {
                try {
                    c.close();
                } catch(SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void executeSql(final String query) {
        workWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        return c.prepareStatement(query);
                    }
                }
        );
    }

    public void executeSql(final String query, String...str) {
        workWithStatementStrategy(
                new StatementStrategy() {
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(query);

                        for(int i = 0; i < str.length; i++) {
                            ps.setString(i+1, str[i]);
                        }

                        return ps;
                    }
                }
        );
    }
}
