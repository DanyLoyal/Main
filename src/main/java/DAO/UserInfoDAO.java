package DAO;

import Config.HibernateConfig;
import Dat.Address;
import Dat.Phonenumber;
import Dat.User;
import Dat.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class UserInfoDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    private static UserInfoDAO instance;

    public static UserInfoDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new UserInfoDAO();
        }
        return instance;
    }

    public UserInfo saveUserInfo(UserInfo userInfo, User user){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            user.setUserInfo(userInfo);

            em.merge(user);
            em.persist(userInfo);
            em.getTransaction().commit();
        }
        return userInfo;
    }

    public UserInfo getUserInfoByID(int id){
        UserInfo foundUserInfo;
        try(EntityManager em = emf.createEntityManager()){
            foundUserInfo = em.find(UserInfo.class, id);
        }
        return foundUserInfo;
    }

    public UserInfo updateEmail(UserInfo userInfo, String email){
        UserInfo updatedInfo;
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            userInfo.setEmail(email);
            updatedInfo = em.merge(userInfo);
            em.getTransaction().commit();
        }
        return updatedInfo;
    }

    public boolean removeUserInfo(UserInfo userInfo){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Phonenumber.removeByUserID");
            query.setParameter("id", userInfo.getId());
            query.executeUpdate();
            em.getTransaction().commit();
        }
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Address.deleteByUserID");
            query.setParameter("id", userInfo.getId());
            query.executeUpdate();
            em.getTransaction().commit();
        }
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(userInfo);
            em.getTransaction().commit();
            if(em.find(UserInfo.class, userInfo.getId()) == null){
                return true;
            }
        }
        return false;
    }
}
