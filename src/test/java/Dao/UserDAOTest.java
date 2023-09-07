package Dao;

import Config.HibernateConfig;
import DAO.UserDAO;
import Dat.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
    private UserDAO userDAO = UserDAO.getInstance(emf);

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO hobby_info(intereststype) VALUES('GENEREL');").executeUpdate();
            em.createNativeQuery("INSERT INTO hobby_info(intereststype) VALUES('INDENDØRS');").executeUpdate();
            em.createNativeQuery("INSERT INTO hobby_info(intereststype) VALUES('UDENDØRS');").executeUpdate();

            em.createNativeQuery("INSERT INTO hobby (name,wikiLink)  VALUES ('3D-udskrivning','https://en.wikipedia.org/wiki/3D_printing');").executeUpdate();
            em.createNativeQuery("INSERT INTO hobby (name,wikiLink)  VALUES ('Akrobatik','https://en.wikipedia.org/wiki/Acrobatics');").executeUpdate();
            em.createNativeQuery("INSERT INTO hobby (name,wikiLink)  VALUES ('Skuespil','https://en.wikipedia.org/wiki/Acting');").executeUpdate();

            em.createNativeQuery("INSERT INTO public.zipcode (zip, city_name, region_name, municipality_name) VALUES (7900, 'Nykøbing M', 'Region Nordjylland', 'Morsø Kommune');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.zipcode (zip, city_name, region_name, municipality_name) VALUES (7950, 'Erslev', 'Region Nordjylland', 'Morsø Kommune');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.zipcode (zip, city_name, region_name, municipality_name) VALUES (7960, 'Karby', 'Region Nordjylland', 'Morsø Kommune');").executeUpdate();

            em.createNativeQuery("INSERT INTO public.users (firstname, lastname) VALUES ('Carsten', 'Danyalsen');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.users (firstname, lastname) VALUES ('Pelle', 'Nicolaisen');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.users (firstname, lastname) VALUES ('Gangsta', 'G');").executeUpdate();

            em.getTransaction().commit();
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.user_info (user_id, age, email) VALUES (1, 25, 'carsten@danyalsen.dk');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_info (user_id, age, email) VALUES (2, 23, 'pelle@nicolaisen.dk');").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_info (user_id, age, email) VALUES (3, 42, 'gangsta@g.dk');").executeUpdate();

            em.createNativeQuery("INSERT INTO public.address (userinfo_user_id, street_name, number, zip_zip) VALUES (1, 'Carsten Danyalsens Allé', '20', 7900);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.address (userinfo_user_id, street_name, number, zip_zip) VALUES (2, 'Pelle Nicolaisens Vej', '15', 7950);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.address (userinfo_user_id, street_name, number, zip_zip) VALUES (3, 'Gangsta G Boulevard', '99', 7960);").executeUpdate();

            em.createNativeQuery("INSERT INTO public.phonenumber (userinfo_user_id, type, phonenumber) VALUES (1, 'MOBILE', 12345678);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.phonenumber (userinfo_user_id, type, phonenumber) VALUES (1, 'LANDLINE', 87654321);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.phonenumber (userinfo_user_id, type, phonenumber) VALUES (2, 'MOBILE', 87456321);").executeUpdate();

            em.getTransaction().commit();
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (1, 1);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (2, 2);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (3, 3);").executeUpdate();

            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (1, 1);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (2, 2);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (3, 3);").executeUpdate();

            em.getTransaction().commit();
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("truncate address, user_info, users, phonenumber, hobby, hobby_hobby_info, hobby_info, user_hobby, zipcode RESTART IDENTITY;").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void createUser() {
        Zip zip;
        User createdUser;
        try (EntityManager em = emf.createEntityManager()) {
            zip = em.find(Zip.class, 1);
            createdUser = UserDAO.getInstance(emf).createUser(new User("Bent", "Den Store"), new UserInfo("bent@denstore.dk", 142), new Address("Ben den stores vej", zip, "69"));
        }
        assertEquals(4, createdUser.getId());
        assertNotNull(createdUser.getUserInfo());
        assertNotEquals(1, createdUser.getUserInfo().getAddress().getId());
    }

    @Test
    void deleteUser() {
        UserDAO.getInstance(emf).deleteUser(1);
        User foundUser;
        try(EntityManager em = emf.createEntityManager()){
            foundUser = em.find(User.class, 1);
        }
        assertNull(foundUser);
    }

    @Test
    void findUserById() {
        User foundUser = UserDAO.getInstance(emf).findUserById(3);
        assertEquals("Gangsta", foundUser.getFirstname());
        assertEquals("99", foundUser.getUserInfo().getAddress().getNumber());
    }

    @Test
    void findUsers() {
        List<User> foundUsers = UserDAO.getInstance(emf).findUsers();
        assertEquals(3, foundUsers.size());
        assertEquals(1, foundUsers.get(0).getId());
    }

    @Test
    void updateUser() {
        User foundUser;
        try(EntityManager em = emf.createEntityManager()){
            foundUser = em.find(User.class, 1);
            foundUser.setInterest(em.find(Hobby.class, 1));
            UserDAO.getInstance(emf).updateUser(foundUser);
        }
        assertEquals(1, foundUser.getHobbies().size());
    }

    @Test
    void findUsersByHobby() {

    }

    @Test
    void findUsersByPhonenumber() {

    }

    @Test
    void findUsersByCityName() {

    }
}