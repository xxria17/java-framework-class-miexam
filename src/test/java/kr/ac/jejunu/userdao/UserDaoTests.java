package kr.ac.jejunu.userdao;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    DaoFactory daoFactory = new DaoFactory();

    @Test
    public void testGetJeju() throws SQLException, ClassNotFoundException {
        Integer id = 1;
        String name = "hulk";
        String password = "1234";
        UserDao userDao = daoFactory.getUserDao();
        User user = userDao.get(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

    @Test
    public void testInsertJeju() throws SQLException, ClassNotFoundException {
        String name = "hulk";
        String password = "1234";

        UserDao userDao = daoFactory.getUserDao();
        Integer id = userDao.insert(name, password);

        User user = userDao.get(id);

        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }

}
