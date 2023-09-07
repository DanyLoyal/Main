package Dat;

import Config.HibernateConfig;
import DAO.HobbyDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    private  static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");
    public static void main(String[] args) {


    }
}