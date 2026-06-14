package com.aston.dao;

import com.aston.entity.User;
import com.aston.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger log =
            LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public void save(User user) {
        Transaction tr = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tr = session.beginTransaction();

            session.persist(user);

            tr.commit();

            log.info("Пользователь сохранен: {}", user.getEmail());

        } catch (Exception e) {

            try {
                if (tr != null && tr.isActive()) {
                    tr.rollback();
                }
            } catch (Exception rollbackEx) {
                log.error("Ошибка отката транзакции", rollbackEx);
            }

            log.error("Ошибка сохранения пользователя", e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void update(User user) {
        Transaction tr = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tr = session.beginTransaction();

            session.merge(user);

            tr.commit();

            log.info("Пользователь обновлен: {}", user.getId());

        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }
            log.error("Ошибка обновления", e);
        }
    }

    @Override
    public void delete(Long id) {
        Transaction tr = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tr = session.beginTransaction();

            User user = session.find(User.class, id);

            if (user == null) {
                log.warn("Пользователь с id={} не найден", id);
                tr.commit();
                return;
            }

            session.remove(user);

            tr.commit();

            log.info("Пользователь удален: {}", id);

        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback();
            }

            log.error("Ошибка удаления пользователя id={}", id, e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }
}