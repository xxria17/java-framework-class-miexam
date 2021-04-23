package kr.ac.jejunu.userdao;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    private static UserDao userDao;

    String name = "hulk";
    String password = "1234";

    @BeforeAll
    public static void setup() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("getUserDao", UserDao.class);
    }

    @Test
    public void testGetJeju() throws SQLException, ClassNotFoundException {
        Integer id = 1;
        User user = userDao.get(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void testInsertJeju() throws SQLException, ClassNotFoundException {
        Integer id = userDao.insert(name, password);

        User user = userDao.get(id);

        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

}
