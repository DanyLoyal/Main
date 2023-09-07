package Dao;

import Config.HibernateConfig;
import DAO.PhoneNumberDAO;
import Dat.Phonenumber;
import Dat.User;
import Dat.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ClientInfoStatus;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberDAOTest{
private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
private PhoneNumberDAO phoneNumberDAO = PhoneNumberDAO.getInstance(emf);

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
        void createPhoneNumber(){
        UserInfo userInfo;

        Phonenumber phonenumber;
        Phonenumber expectedPhoneNumber;
        try(EntityManager em = emf.createEntityManager()) {
            userInfo = em.find(UserInfo.class, 3);

        }

        phonenumber = new Phonenumber(324234234, Phonenumber.PhoneType.MOBILE);

        expectedPhoneNumber = phoneNumberDAO.createPhoneNumber(phonenumber, userInfo);
        assertEquals(324234234, expectedPhoneNumber.getPhonenumber());
        assertEquals(Phonenumber.PhoneType.MOBILE, expectedPhoneNumber.getPhoneType());

      }

      @Test
    void findPhoneNumberByUserId(){

    List<Phonenumber> actual = phoneNumberDAO.getPhoneNumbersByUserId(1);
    List<Phonenumber> actual2 = phoneNumberDAO.getPhoneNumbersByUserId(2);
    Phonenumber expected2 = new Phonenumber(87456321, Phonenumber.PhoneType.MOBILE);
    Phonenumber expected = new Phonenumber(12345678, Phonenumber.PhoneType.MOBILE);

    assertEquals(87654321, actual.get(1).getPhonenumber());
    assertEquals(expected.getPhonenumber(),actual.get(0).getPhonenumber());
    assertEquals(expected2.getPhonenumber(), actual2.get(0).getPhonenumber());
    assertEquals(Phonenumber.PhoneType.MOBILE, actual.get(0).getPhoneType());
    assertEquals(Phonenumber.PhoneType.LANDLINE, actual.get(1).getPhoneType());
      }

    @Test
    void updatePhoneNumber() {

    int phoneNumberIdToUpdate = 1;

    Phonenumber existingPhoneNumber;

    try(EntityManager em = emf.createEntityManager()) {
        existingPhoneNumber = em.find(Phonenumber.class, 1);

    }

    assertNotEquals(24678866, existingPhoneNumber.getPhonenumber());
    assertEquals(1, existingPhoneNumber.getId());


    phoneNumberDAO.updatePhoneNumber(existingPhoneNumber, 24678866);
    assertEquals(1, existingPhoneNumber.getId());
    assertEquals(24678866, existingPhoneNumber.getPhonenumber());

    }

    @Test
    void deletePhoneNumber() {

    Phonenumber phonenumber;

        try(EntityManager em = emf.createEntityManager()) {

            assertNotNull(em.find(Phonenumber.class, 1));

        }
    phoneNumberDAO.deletePhoneNumber(1);

        try(EntityManager em = emf.createEntityManager()) {

            assertNull(em.find(Phonenumber.class, 1));

        }

    }

    @Test
    void findPhoneNumberById() {

    Phonenumber phonenumber = phoneNumberDAO.findPhoneNumberById(1);
    assertEquals(1, phonenumber.getUserInfo().getId());
    assertEquals(12345678, phonenumber.getPhonenumber());
    }
}