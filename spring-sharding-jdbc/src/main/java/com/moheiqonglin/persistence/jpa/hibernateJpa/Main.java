package com.moheiqonglin.persistence.jpa.hibernateJpa;


import com.moheiqonglin.persistence.jpa.entities.Users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author wanli.zhou
 * @description
 * @time 28/12/2018 4:51 PM
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-jpa-unit");
        EntityManager em = entityManagerFactory.createEntityManager();

        query(em, 2L);
        queryAndUpdate(em, 2L, new Users(2L, "name-2-hb", true));
        query(em, 2L);
        createUser(em, new Users(6L, "name-6", false));
        query(em, 5L);
        em.close();
    }

    private static void queryAndUpdate(EntityManager em, Long id, Users users) {
        em.getTransaction().begin();
        Users user = em.createQuery("SELECT u FROM Users u where u.id = :id", Users.class)
                .setParameter("id", id).getResultList().stream().findFirst().get();
        user.setName(users.getName());
        user.setSex(users.getSex());
        em.getTransaction().commit();
    }

    private static void createUser(EntityManager em, Users user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    private static void query(EntityManager em, long id) {
        Users user = em.createQuery("SELECT u FROM Users u where u.id = :id", Users.class)
                .setParameter("id", id).getResultList().stream().findFirst().get();

        System.out.println(user);
    }
}