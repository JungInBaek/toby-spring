//package springbook.user.dao;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DaoFactory {
//
//    @Bean
//    public UserDao userDao() {
//        UserDaoJdbc userDao = new UserDaoJdbc();
//        userDao.setDataSource(dataSource());
//
//        return userDao;
//    }
//
////    public AccountDao accountDao() {
////       return new AccountDao(connectionMaker());
////    }
//
////    public MessageDao messageDao() {
////        return new MessageDao(connectionMaker());
////    }
//
//    @Bean
//    public DataSource dataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(org.h2.Driver.class);
//        dataSource.setUrl("jdbc:h2:~/test");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//
//        return dataSource;
//    }
//}
