package DAO;

import Config.HibernateConfig;
import Dat.Hobby;
import Dat.HobbyInfo;
import Dat.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.*;

public class HobbyDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    private static HobbyDAO instance;

    public static HobbyDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }

    public Hobby saveHobby(Hobby hobby){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        }
        return hobby;
    }

    public boolean removeHobby(Hobby hobby){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            if(hobby.deleteHobby()) {
                em.merge(hobby);
            }
            em.getTransaction().commit();
        }
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(hobby);
            em.getTransaction().commit();
            if(em.find(Hobby.class, hobby.getId()) == null){
                return true;
            }
        }
        return false;
    }

    public Hobby updateHobbyTypes(Hobby hobby, HobbyInfo info){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            hobby.setInterestsInfo(info);
            em.merge(hobby);
            em.merge(info);
            em.getTransaction().commit();
        }
        return hobby;
    }

    public Hobby getHobbyById(int id){
        Hobby foundHobby;
        try(EntityManager em = emf.createEntityManager()){
            foundHobby = em.find(Hobby.class, id);
        }
        return foundHobby;
    }

    //***** US - 4 *****\\
    public int findAmountOfUsersForHobby(int hobbyId){
        int size = 0;

        try (EntityManager em = emf.createEntityManager()){
            List<Integer> userIds = em.createNativeQuery("SELECT * FROM user_hobby WHERE hobby_id = "+hobbyId+";").getResultList();
            size = userIds.size();
        }
        return size;
    }

    //***** US - 5 *****\\
    public Map<Hobby, Integer> findAllHobbiesAndItsUsers(){
        Map<Hobby, Integer> hobbies = new HashMap<>();

        try (EntityManager em = emf.createEntityManager()){
            List<Hobby> hobbyList = em.createNamedQuery("Hobby.findAllHobbies", Hobby.class).getResultList();

            for(Hobby u: hobbyList){
                hobbies.put(u, u.getUsers().size());
            }
        }
        return hobbies;
    }
}
