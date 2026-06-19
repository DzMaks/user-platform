package com.aston.dao;

import com.aston.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class UserDaoIntegrationTest extends AbstractDaoIntegrationTest {

    private final UserDao  dao = new UserDaoImpl();

    @Test
    void shouldSaveUser() {
        //given
        User user = new User(
                "Alex",
                "alex@mail.com",
                25
        );

        //when
        dao.save(user);

        User result = dao.findByEmail("alex@mail.com");

        //then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void shouldFindUserById() {
        //given
        User user = new User(
                "Ivan",
                "ivanov@mail.com",
                24
        );

        dao.save(user);

        Long id = user.getId();

        //when
        User result = dao.findById(id);

        //then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getAge(), result.getAge());
    }

    @Test
    void shouldFindAllUsers() {
        //given
        List<User> users = List.of(
                new User("Petr", "petr@mail.ru", 65),
                new User("Andrew", "andrew@mail.ru", 29)
        );

        dao.save(users.get(0));
        dao.save(users.get(1));

        //when
        List<User> result = dao.findAll();

        //then
        assertEquals(users.size(), result.size());
    }

    @Test
    void shouldReturnNullWhenUserNotFoundById() {
        //given
        Long id = 34L;

        //when
        User result = dao.findById(id);

        //then
        Assertions.assertNull(result);
    }

    @Test
    void shouldReturnNullWhenEmailNotFound() {
        //given
        String mail = "alex@mail.com";

        //when
        User result = dao.findByEmail(mail);

        //then
        assertNull(result);
    }

    @Test
    void shouldFindUserByEmail() {
        //given
        User user = new User(
                "Ivan",
                "ivanov@mail.com",
                24
        );

        dao.save(user);

        //when
        User result = dao.findByEmail(user.getEmail());

        //then
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getAge(), result.getAge());
    }

    @Test
    void shouldDeleteUser() {
        //given
        User user = new User(
                "Ivan",
                "ivanov34@mail.com",
                21
        );

        dao.save(user);
        Long id = user.getId();

        //when
        dao.delete(id);
        User result = dao.findById(id);

        //then
        assertNull(result);
    }

    @Test
    void shouldUpdateUser() {
        //given
        User user = new User(
                "Sergei",
                "sergei34@mail.com",
                18
        );

        dao.save(user);

        user.setAge(17);
        user.setEmail("seryi@mail.ru");

        //when
        dao.update(user);
        User result = dao.findById(user.getId());

        //then
        assertNotNull(result);
        assertEquals("Sergei", result.getName());
        assertEquals(17, result.getAge());
        assertEquals("seryi@mail.ru", result.getEmail());


    }

}
