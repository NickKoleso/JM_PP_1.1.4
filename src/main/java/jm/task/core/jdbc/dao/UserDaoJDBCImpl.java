package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
    private Connection connection = util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE Users (id BIGINT NOT NULL AUTO_INCREMENT primary key, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age TINYINT NOT NULL);");
            System.out.println("Таблица создана успешно");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table Users");
            System.out.println("Таблица удалена успешно");
        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO Users (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', '" + age + "')");
            System.out.println("User с именем- " + name + " добавлен в базу данных");
        } catch (SQLException throwable) {
            System.out.println("Ошибка при добавление User");
            throwable.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users WHERE ID = " + id + " ");
            System.out.println("User удален успешно");
        } catch (SQLException throwable) {
            System.out.println("Ошибка при удаление User");
            throwable.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name, lastName, age FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM Users");
            System.out.println("Очистка таблицы проведена успешно");

        } catch (SQLException throwable) {
            System.out.println("Ошибка при очистке таблицы");
            throwable.printStackTrace();
        }
    }
}
