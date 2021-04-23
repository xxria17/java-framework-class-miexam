package kr.ac.jejunu.userdao;

import java.sql.*;

public class UserDao {
    JdbcContext jdbcContext;
    UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }
    public User get(Integer id) throws SQLException {
        Object[] objects = {id};
        StatementStrategy statementStrategy = new GetStatementStrategy(objects);
        return jdbcContext.jdbcContextGet(statementStrategy);
    }

    public Integer insert(String name, String password) throws SQLException {
        Object[] objects = {name, password};
        StatementStrategy statementStrategy = new InsertStatementStrategy(objects);
        return jdbcContext.jdbcContextInsert(statementStrategy);
    }


    public void update(User user) throws SQLException {
        Object[] objects = {user.getName(), user.getPassword(), user.getId()};
        StatementStrategy statementStrategy = new UpdateStatementStrategy(objects);
        jdbcContext.jdbcContextUpdate(statementStrategy);

    }

    public void delete(Integer id) throws SQLException {
        Object[] objects = {id};
        StatementStrategy statementStrategy = new DeleteStatementStrategy(objects);
        jdbcContext.jdbcContextDelete(statementStrategy);

    }

}
