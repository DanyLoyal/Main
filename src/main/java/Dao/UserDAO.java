package DAO;

import Dat.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static EntityManagerFactory emf;

    private static UserDAO instance;


    //****** Singleton *****\\

    public static UserDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

    public User createUser(User user, UserInfo userInfo, Address address) {
        user.setUserInfo(userInfo);
        userInfo.setAddress(address);
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    public User updateUser(User user) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().begin();
            return user;
        }
    }

    public void deleteUser(int id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = findUserById(id);
            if (user != null) {
                em.remove(user);
                em.getTransaction().commit();
            }
        }
    }

    //***** US - 1 *****\\
    public User findUserById(int id) {
        try (var em = emf.createEntityManager()) {
            User user = em.find(User.class, id);
            if (user != null) {
                return user;
            }
            return null;
        }
    }

    public List<User> findUsers() {
        try (var em = emf.createEntityManager()) {
            TypedQuery<User> namedQuery = em.createNamedQuery("User.findAll", User.class);
            return namedQuery.getResultList();
        }
    }

    public List<User> findUsersByCityName(String cityName){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery query = em.createNamedQuery("User.findByZip", User.class);
            query.setParameter("cityName", cityName);
            return query.getResultList();
        }
    }

    public User findUserByPhonenumber(Phonenumber phonenumber){
        try(EntityManager em = emf.createEntityManager()){
            Phonenumber foundPhone = em.find(Phonenumber.class, phonenumber.getId());
            return foundPhone.getUserInfo().getUser();
        }
    }

    //***** US - 3 *****\\
    public List<User> findUsersByHobby(int hobbyId){
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery usersByHobby = em.createNamedQuery("User.findUsersByHobby", User.class);
            usersByHobby.setParameter("id",hobbyId);
            return usersByHobby.getResultList();
        }
    }
}
