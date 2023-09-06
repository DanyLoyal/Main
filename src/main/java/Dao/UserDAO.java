package Dao;

import Dat.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO {

    private static EntityManagerFactory emf;

    private static UserDAO instance;


    //****** Singleton *****\\

    public static UserDAO getInstance(EntityManagerFactory _emf){
        if (instance == null){
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }

public User createUser (User user){
            try (var em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
}

public User updateUser (User user){
    try (var em = emf.createEntityManager()){
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().begin();
        return user;
    }
}

public void deleteUser(int id){
        try (var em = emf.createEntityManager()){
            em.getTransaction().begin();
            User user = findUserById(id);
            if(user != null){
                em.remove(user);
                em.getTransaction().commit();
            }
        }
}

public User findUserById(int id){
        try(var em = emf.createEntityManager()){
            User user = em.find(User.class, id);
            if (user != null){
                return user;
            }
            return null;
        }
}

public List<User> findUsers (){
        try (var em = emf.createEntityManager()){
            TypedQuery<User> namedQuery = em.createNamedQuery("User.findAll", User.class);
            return namedQuery.getResultList();
        }
}










}
