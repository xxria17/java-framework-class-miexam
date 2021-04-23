package kr.ac.jejunu.userdao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;

public class UserDao {
    JdbcTemplate jdbcTemplate;
    UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public User get(Integer id) {
        Object[] objects = {id};
        String sql = "select * from userinfo where id = ?";
        return jdbcTemplate.query(sql, objects, rs -> {
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        });
    }

    public Integer insert(String name, String password) {
        Object[] objects = {name, password};
        String sql = "insert into userinfo (name,password) values (?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                    con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i+1, objects[i]);
            }
            return preparedStatement;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(User user) {
        Object[] objects = {user.getName(), user.getPassword(), user.getId()};
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        jdbcTemplate.update(sql, objects);
    }

    public void delete(Integer id) {
        Object[] objects = {id};
        String sql = "delete from userinfo where id = ?";
        jdbcTemplate.update(sql, objects);
    }

}
