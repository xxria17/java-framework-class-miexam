package kr.ac.jejunu.userdao;

import java.sql.*;

public class UserDao {
    JdbcContext jdbcContext;
    UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }
    public User get(Integer id) throws SQLException {
        Object[] objects = {id};
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from userinfo where id = ?");
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        return jdbcContext.jdbcContextGet(statementStrategy);
    }

    public Integer insert(String name, String password) throws SQLException {
        Object[] objects = {name, password};
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into userinfo (name,password) values (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        return jdbcContext.jdbcContextInsert(statementStrategy);
    }


    public void update(User user) throws SQLException {
        Object[] objects = {user.getName(), user.getPassword(), user.getId()};
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update userinfo set name = ?, password = ? where id = ?");
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        jdbcContext.jdbcContextUpdate(statementStrategy);

    }

    public void delete(Integer id) throws SQLException {
        Object[] objects = {id};
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from userinfo where id = ?");
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        jdbcContext.jdbcContextDelete(statementStrategy);

    }

}
