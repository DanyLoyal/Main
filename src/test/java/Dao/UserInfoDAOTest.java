package Dao;

import Config.HibernateConfig;
import DAO.HobbyDAO;
import DAO.UserDAO;
import DAO.UserInfoDAO;
import Dat.User;
import Dat.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoDAOTest {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
    private UserInfoDAO userInfoDAO = UserInfoDAO.getInstance(emf);

    @BeforeEach
    void setUp(){
        try(EntityManager em = emf.createEntityManager()) {
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

        try(EntityManager em = emf.createEntityManager()){
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

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (1, 1);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (2, 2);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (3, 3);").executeUpdate();

            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (1, 1);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (2, 2);").executeUpdate();
            em.createNativeQuery("INSERT INTO public.hobby_hobby_info (hobby_id, hobby_info_id) VALUES (3, 3);").executeUpdate();

            em.getTransaction().commit();
        }

        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown(){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createNativeQuery("truncate address, user_info, users, phonenumber, hobby, hobby_hobby_info, hobby_info, user_hobby, zipcode restart identity;").executeUpdate();
            em.getTransaction().commit();
        }
    }
    @Test
    void removeUserInfo(){
        UserInfo userInfo;
        try (EntityManager em = emf.createEntityManager()) {
            userInfo = em.find(UserInfo.class, 1);
        }
            Boolean removeCheck = userInfoDAO.removeUserInfo(userInfo);
            assertEquals(true, removeCheck);

    }

  /*  @Test
    void saveUserInfo() {
        UserInfo userInfo;
        UserInfo newUserInfo;
        UserInfo testUserInfo;
        User user;
        try (EntityManager em = emf.createEntityManager()) {
            userInfo = em.find(UserInfo.class, 1);
            user = em.find(User.class, 1);
        }

        userInfoDAO.removeUserInfo(userInfo);
        newUserInfo = new UserInfo("newmail@mail.com", 25);



        testUserInfo = userInfoDAO.saveUserInfo(newUserInfo, user);

        assertEquals(25, testUserInfo.getAge());
        assertEquals("newmail@mail.com", testUserInfo.getEmail());


    }

   */

    @Test
    void getUserInfoByID() {
        UserInfo userInfo = userInfoDAO.getUserInfoByID(1);

        assertEquals(25, userInfo.getAge());
        assertEquals("carsten@danyalsen.dk", userInfo.getEmail());
        assertEquals("Carsten", userInfo.getUser().getFirstname());
        assertEquals("Danyalsen", userInfo.getUser().getLastname());


    }

    @Test
    void updateEmail() {
        UserInfo userInfo;
        UserInfo newUserInfo;
        try(EntityManager em = emf.createEntityManager()){
            userInfo = em.find(UserInfo.class, 1);
        }

        newUserInfo = userInfoDAO.updateEmail(userInfo, "nymail@mail.com");

        assertEquals("nymail@mail.com", newUserInfo.getEmail());

    }
}