package com.aston.dao;

import com.aston.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractDaoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");

    @BeforeAll
    static void setup() {
        postgres.start();

        System.setProperty(
                "hibernate.connection.url",
                postgres.getJdbcUrl()
        );

        System.setProperty(
                "hibernate.connection.username",
                postgres.getUsername()
        );

        System.setProperty(
                "hibernate.connection.password",
                postgres.getPassword()
        );
    }

    @AfterEach
    void cleanDataBase() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createMutationQuery("delete FROM User").executeUpdate();

            session.getTransaction().commit();
        }
    }

}
