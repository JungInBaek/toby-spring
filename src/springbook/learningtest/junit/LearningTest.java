package springbook.learningtest.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class LearningTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    SimpleDriverDataSource autoDataSource;

    static DataSource testDataSource;

    @Test
    public void singletonTest1() {
        assertTrue(testDataSource == null || testDataSource == autoDataSource && autoDataSource == context.getBean("dataSource", DataSource.class));
    }

    @Test
    public void singletonTest2() {
        assertTrue(testDataSource == null || testDataSource == autoDataSource && autoDataSource == context.getBean("dataSource", DataSource.class));
    }

    @Test
    public void singletonTest3() {
        assertTrue(testDataSource == null || testDataSource == autoDataSource && autoDataSource == context.getBean("dataSource", DataSource.class));
    }

    @Test
    public void dataSourceValuePropertyTest() throws ClassNotFoundException {
        assertThat("org.h2.Driver", is(autoDataSource.getDriver().getClass().getName()));
        assertThat("jdbc:h2:tcp://localhost/~/springbook", is(autoDataSource.getUrl()));
        assertThat("spring", is(autoDataSource.getUsername()));
        assertThat("book", is(autoDataSource.getPassword()));
    }
}
