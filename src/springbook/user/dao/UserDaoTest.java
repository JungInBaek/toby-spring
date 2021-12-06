package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    //  테스트
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        UserDao dao1 = context.getBean("userDao", UserDao.class);
//        UserDao dao2 = context.getBean("userDao", UserDao.class);
//
//        System.out.println(dao1);
//        System.out.println(dao2);

        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
