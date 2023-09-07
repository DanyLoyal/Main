package Dat;

import Config.HibernateConfig;
import DAO.AddressDAO;
import DAO.UserDAO;
import DAO.UserInfoDAO;
import DAO.ZipDAO;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    private  static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
    public static void main(String[] args) {

        UserDAO.getInstance(emf).createUser(new User("Luna", "Rosendahl"), new UserInfo("luna@luna.dk", 2), new Address("Vej vej", ZipDAO.getInstance(emf).getCityByZip(2800), "1"));

    }
}