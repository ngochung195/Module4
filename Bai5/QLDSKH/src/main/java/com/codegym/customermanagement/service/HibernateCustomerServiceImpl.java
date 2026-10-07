package com.codegym.customermanagement.service;

import com.codegym.customermanagement.model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HibernateCustomerServiceImpl implements ICustomerService {

    private static SessionFactory sessionFactory;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    @Override
    public List<Customer> findAll() {
        String queryStr = "SELECT c FROM Customer AS c";
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery(queryStr, Customer.class).getResultList();
        }
    }

    @Override
    public Customer findById(Long id) {
        String queryStr = "SELECT c FROM Customer AS c WHERE c.id = :id";
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery(queryStr, Customer.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public void save(Customer customer) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (customer.getId() == null) {
                session.persist(customer);
            } else {
                session.merge(customer);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving customer: " + e.getMessage(), e);
        }
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if (customer != null) {
            Transaction transaction = null;
            try (Session session = getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.remove(session.contains(customer) ? customer : session.merge(customer));
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("Error removing customer: " + e.getMessage(), e);
            }
        }
    }
}
