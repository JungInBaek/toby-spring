package springbook.user.dao;

import org.h2.api.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {

    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));

                    return user;
                }
            };

    public void setDataSource(DataSource dataSource) {
        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //  추가
    public void add(final User user) throws DuplicateUserIdException {
//        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());

        try {
            jdbcContext.executeSql("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
        } catch(SQLException e) {
            if(e.getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                throw new DuplicateUserIdException(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    //  조회
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return jdbcTemplate.queryForInt("select count(*) from users");
    }
}
