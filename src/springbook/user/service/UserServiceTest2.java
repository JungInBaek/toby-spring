package springbook.user.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import springbook.user.dao.UserDao;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;


//  컨테이너에 종속적이지 않은 테스트
public class UserServiceTest2 {

    DataSource dataSource;
    UserDao userDao;
    UserService userService;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("erwins", "신승한", "p3", Level.SILVER, MIN_RECOMMEND_FOR_GOLD-1, 29),
                new User("madnite1", "이상호", "p4", Level.SILVER, MIN_RECOMMEND_FOR_GOLD, 30),
                new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );

        dataSource = new SingleConnectionDataSource("jdbc:h2:tcp://localhost/~/testdb", "spring", "book", true);
        userDao = new UserDaoJdbc(dataSource);
        userService = new UserService();
        userService.setUserDao(userDao);
    }

    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void upgradeLevels() {

        userDao.deleteAll();
        for(User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if(upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

//    private void checkLevel(User user, Level expectedLevel) {
//        User userUpdate = userDao.get(user.getId());
//        assertThat(userUpdate.getLevel(), is(expectedLevel));
//    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);      //  GOLD 레벨
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }
}
