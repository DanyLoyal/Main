package Dao;

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


    private EntityManagerFactory emf;
    private EntityManager em;

    public PhoneNumberDAO(){
        emf = Persistence.createEntityManagerFactory("hobby");
        em = emf.createEntityManager();
    }

    public void createPhoneNumber(Phonenumber phonenumber) {
        EntityManager localEm = emf.createEntityManager();
        try {
        em.getTransaction().begin();
        em.persist(phonenumber);
        em.getTransaction().commit();
    }finally {
            if (localEm != null && localEm.isOpen())
                localEm.close();
        }
    }

    public List<Phonenumber> getPhoneNumbersByUser(User user) {
        EntityManager localEm = emf.createEntityManager();
        try {
            TypedQuery<Phonenumber> query = em.createQuery("SELECT u FROM Phonenumber u WHERE u.userInfo = :user", Phonenumber.class);
            query.setParameter("user", user);
            return query.getResultList();
        }finally {
            if (localEm != null && localEm.isOpen())
                localEm.close();
        }
    }
}
