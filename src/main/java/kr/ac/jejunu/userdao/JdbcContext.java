package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    DataSource dataSource;
    JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    User jdbcContextGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }

        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //리턴
        return user;
    }

    int jdbcContextInsert(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int id;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //리턴
        return id;
    }

    void jdbcContextUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.execute();

        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    User get(Object[] objects, String sql) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        return jdbcContextGet(statementStrategy);
    }

    int insert(Object[] objects, String sql) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        return jdbcContextInsert(statementStrategy);
    }

    void update(Object[] objects, String sql) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        };
        jdbcContextUpdate(statementStrategy);
    }
}
