package com.aston.service;

import com.aston.dao.UserDao;
import com.aston.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(userDao);
    }

    @Test
    void createUserIfValid() {
        //given
        User user = new User(
                "Ivan",
                "ivan@mail.ru",
                30
        );

        when(userDao.findByEmail(user.getEmail())).thenReturn(null);

        //when
        service.create(user);

        //then
        verify(userDao).save(user);
    }

    @Test
    void  createUserIfMailTaken() {
        //given
        User user = new User(
                "Sergei",
                "sergei@mail.ru",
                19
        );

        User existingUser = new User(
                "Seryi",
                "sergei@mail.ru",
                23
        );

        when(userDao.findByEmail(user.getEmail())).thenReturn(existingUser);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(user));

        //then
        assertEquals("Пользователь с такой почтой уже существует", exception.getMessage());

        verify(userDao, never()).save(user);
    }

    @Test
    void createUserIfAgeNotValid() {
        //given
        User user = new User(
                "Alex",
                "alex@mail.ru",
                -5
        );

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(user));

        //then
        assertEquals("Возраст не может быть отрицательным", exception.getMessage());

        verify(userDao, never()).save(user);
    }

    @Test
    void getByIdShouldReturnUser() {
        //given
        Long id = 543L;

        User user = new User(
                "Anatoliy",
                "anatoliy@mail.ru",
                25
        );

        when(userDao.findById(id)).thenReturn(user);

        //when
        User result = service.getById(id);

        //then
        assertEquals(user, result);

        verify(userDao).findById(id);
    }

    @Test
    void getAllShouldReturnUsers() {
        //given
        List<User> users = List.of(
                new User("Petr", "petr@mail.ru", 65),
                new User("Andrew", "andrew@mail.ru", 29)
        );

        when(userDao.findAll()).thenReturn(users);

        //when
        List<User> result = service.getAll();

        //then
        assertEquals(users, result);

        verify(userDao).findAll();
    }

    @Test
    void updateShouldCallDao() {
        //given
        User user = new User(
                "Bob",
                "bob@mail.ru",
                27
        );

        //when
        service.update(user);

        //then
        verify(userDao).update(user);
    }

    @Test
    void deleteShouldCallDao() {
        //given
        Long id = 34L;

        //when
        service.delete(id);

        //then
        verify(userDao).delete(id);
    }

}
