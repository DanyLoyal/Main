package DAO;

import Config.HibernateConfig;
import Dat.Address;
import Dat.Zip;
import jakarta.persistence.*;

import java.util.*;

public class AddressDAO {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

    private static AddressDAO instance;

    public static AddressDAO getInstance(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new AddressDAO();
        }
        return instance;
    }

    public Address saveAddress(Address address){
        try(EntityManager em = emf.createEntityManager()){

            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        }
        return address;
    }


    public Address updateAddress(Address address){
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(address);
            em.getTransaction().commit();
        }
        return address;
    }

    public Address findAddressByUserId(int user_id){
        Address foundaddress;
        try(EntityManager em = emf.createEntityManager()){
            foundaddress = em.find(Address.class, user_id);
        }
        return foundaddress;
    }


    //todo: maybe add removeAddress method
}
