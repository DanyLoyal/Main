package DAO;

import Config.HibernateConfig;
import Dat.Hobby;
import Dat.HobbyInfo;
import Dat.Zip;
import jakarta.persistence.*;

import java.util.*;

public class ZipDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    private static ZipDAO instance;

    public static ZipDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new ZipDAO();
        }
        return instance;
    }

    public Zip saveZip(Zip zip){
        try(EntityManager em = emf.createEntityManager()){

            em.getTransaction().begin();
            em.persist(zip);
            em.getTransaction().commit();
        }
        return zip;
    }


    public Zip updateZip(Zip zip){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
                em.merge(zip);
            em.getTransaction().commit();
        }
        return zip;
    }

    public Zip getCityByZip(int zipcodecheck){
        Zip foundzip;
        try(EntityManager em = emf.createEntityManager()){
            foundzip = em.find(Zip.class, zipcodecheck);
        }
        return foundzip;
    }


    //***** US - 7 *****\\
    public List<Zip> getCitiesAndPostcodes (){

        try (EntityManager em = emf.createEntityManager()){

            TypedQuery<Zip> typedQuery = em.createNamedQuery("Zip.findAll", Zip.class);

            return typedQuery.getResultList();
        }
    }

    //todo: maybe add removeZip method
}
