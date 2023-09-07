package DAO;

import Config.HibernateConfig;
import Dat.Hobby;
import Dat.HobbyInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

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
            if(em.find(Hobby.class, hobby) == null){
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

    public int getNumberofUsersForHobby (int hobbyId){
        int foundNumber = 0;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery typedQuery = em.createNamedQuery("Hobby.findHobbyUsersByHobbyId", Hobby.class);
            typedQuery.setParameter("hobby_id", hobbyId);
            foundNumber = typedQuery.getResultList().size();

        }
        return foundNumber;
    }
}
