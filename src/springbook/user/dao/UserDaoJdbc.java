package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlService.SqlService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {

//    private JdbcContext jdbcContext;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setLevel(Level.valueOf(rs.getInt("level")));
                    user.setLogin(rs.getInt("login"));
                    user.setRecommend(rs.getInt("recommend"));
                    user.setEmail(rs.getString("email"));

                    return user;
                }
            };

    private SqlService sqlService;

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    public void setDataSource(DataSource dataSource) {
//        jdbcContext = new JdbcContext();
//        jdbcContext.setDataSource(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //  생성자
    public UserDaoJdbc(DataSource dataSource) {
//        jdbcContext = new JdbcContext();
//        jdbcContext.setDataSource(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDaoJdbc() {
    }

    //  추가
    public void add(final User user) throws DuplicateUserIdException {
//        jdbcContext.executeSql("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());

        jdbcTemplate.update(sqlService.getSql("userAdd"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    //  조회
    public User get(String id) {
        return jdbcTemplate.queryForObject(sqlService.getSql("userGet"), new Object[]{id}, userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(sqlService.getSql("userGetAll"), userMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
        return jdbcTemplate.queryForInt(sqlService.getSql("userGetCount"));
    }

    public void update(User user) {
        jdbcTemplate.update(sqlService.getSql("userUpdate"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
    }
}
