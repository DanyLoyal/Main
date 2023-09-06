package DAO;

import Config.HibernateConfig;
import Dat.HobbyInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class HobbyInfoDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    private static HobbyInfoDAO instance;

    public static HobbyInfoDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new HobbyInfoDAO();
        }
        return instance;
    }

    public HobbyInfo saveHobbyInfo(HobbyInfo hobbyInfo){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(hobbyInfo);
            em.getTransaction().commit();
        }
        return hobbyInfo;
    }

    public boolean deleteHobbyInfo(HobbyInfo hobbyInfo){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            if(hobbyInfo.deleteHobbyInfo()){
                em.merge(hobbyInfo);
            }
            em.getTransaction().commit();
        }
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(hobbyInfo);
            em.getTransaction().commit();
            if(em.find(HobbyInfo.class, hobbyInfo) == null){
                return true;
            }
            return false;
        }
    }

    public HobbyInfo updateInterestType(HobbyInfo hobbyInfo, HobbyInfo.InterestsType interestsType){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            hobbyInfo.setInterestsType(interestsType);
            em.merge(hobbyInfo);
            em.getTransaction().commit();
        }
        return hobbyInfo;
    }

    public HobbyInfo findHobbyInfoByID(int id){
        HobbyInfo foundHobbyInfo;
        try(EntityManager em = emf.createEntityManager()){
            foundHobbyInfo = em.find(HobbyInfo.class, id);
        }
        return foundHobbyInfo;
    }
}
