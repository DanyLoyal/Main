package DAO;

import Dat.Phonenumber;
import Dat.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PhoneNumberDAO {


    private static EntityManagerFactory emf;
    private static PhoneNumberDAO instance;

    public static PhoneNumberDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneNumberDAO();
        }
        return instance;
    }

    public void createPhoneNumber(Phonenumber phonenumber) {
        try (EntityManager localEm = emf.createEntityManager()) {
            localEm.getTransaction().begin();
            localEm.persist(phonenumber);
            localEm.getTransaction().commit();
        }
    }

    public List<Phonenumber> getPhoneNumbersByUserId(int userId) {
        try (EntityManager localEm = emf.createEntityManager()) {
            TypedQuery<Phonenumber> query = localEm.createQuery("SELECT u FROM Phonenumber u WHERE u.userInfo.id = :userId", Phonenumber.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }
    }

    public Phonenumber updatePhoneNumber(Phonenumber phonenumber) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(phonenumber);
            em.getTransaction().begin();
            return phonenumber;
        }
    }

    public void deletePhoneNumber(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Phonenumber phonenumber = findPhoneNumberById(id);
            if (phonenumber != null) {
                em.remove(phonenumber);
                em.getTransaction().commit();
            }
        }
    }

    public Phonenumber findPhoneNumberById(int id) {
        try (var em = emf.createEntityManager()) {
            Phonenumber phonenumber = em.find(Phonenumber.class, id);
            if (phonenumber != null) {
                return phonenumber;
            }
            return null;
        }
    }
}
