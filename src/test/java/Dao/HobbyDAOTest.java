package Dao;

import Config.HibernateConfig;
import DAO.HobbyDAO;
import DAO.UserDAO;
import Dat.Hobby;
import Dat.HobbyInfo;
import Dat.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Dat.HobbyInfo.InterestsType.UDENDØRS;
import static org.junit.jupiter.api.Assertions.*;

class HobbyDAOTest {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobby_test");
    private HobbyDAO hobbyDAO = HobbyDAO.getInstance(emf);

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
    void saveHobby() {

        Hobby hobby;
        Hobby expectedHobby;
        hobby = new Hobby("Ringridning", "https://da.wikipedia.org/wiki/Ringridning");

        expectedHobby = hobbyDAO.saveHobby(hobby);
        assertEquals(4, expectedHobby.getId());
        assertEquals("Ringridning", expectedHobby.getName());
        assertEquals("https://da.wikipedia.org/wiki/Ringridning", expectedHobby.getLink());


    }

    @Test
    void updateHobbyTypes() {
        Hobby hobby;
        Hobby expectedHobby;
        HobbyInfo hobbyInfo;

        try(EntityManager em = emf.createEntityManager()) {

            hobby = new Hobby("Ringridning", "https://da.wikipedia.org/wiki/Ringridning");
            hobbyInfo = em.find(HobbyInfo.class, 3);
            expectedHobby = hobbyDAO.updateHobbyTypes(hobby, hobbyInfo);

            assertEquals(1, expectedHobby.getHobbyInfos().size());

        }
    }


    @Test
    void removeHobby() {

        Hobby hobby;
        HobbyInfo hobbyInfo;
        try(EntityManager em = emf.createEntityManager()) {

            hobby = new Hobby("Ringridning", "https://da.wikipedia.org/wiki/Ringridning");
            hobbyInfo = em.find(HobbyInfo.class, 3);
            hobbyDAO.updateHobbyTypes(hobby, hobbyInfo);
            hobbyDAO.saveHobby(hobby);

            Boolean removeCheck = hobbyDAO.removeHobby(hobby);
            assertEquals(true, removeCheck);
        }
    }



    @Test
    void getHobbyById() {

        assertEquals("Akrobatik", hobbyDAO.getHobbyById(2).getName());
        assertEquals("https://en.wikipedia.org/wiki/Acrobatics", hobbyDAO.getHobbyById(2).getLink());
    }

    @Test
    void findAmountOfUsersForHobby() {

        assertEquals(1, hobbyDAO.findAmountOfUsersForHobby(1));
        assertEquals(1, hobbyDAO.findAmountOfUsersForHobby(2));
        assertEquals(1, hobbyDAO.findAmountOfUsersForHobby(3));

    }


    @Test
    void findAllHobbiesAndItsUsers() {
            try(EntityManager em = emf.createEntityManager()){
                em.getTransaction().begin();
                em.createNativeQuery("INSERT INTO public.user_hobby (user_id, hobby_id) VALUES (2, 1);").executeUpdate();
                em.getTransaction().commit();
            }
            List<Hobby> hobbyList = new ArrayList<>();
            List<Integer> userList = new ArrayList<>();

            Map<Hobby, Integer> hobbies = new HashMap<>();
            hobbies = hobbyDAO.findAllHobbiesAndItsUsers();
            System.out.println("Found hobbies are: " + hobbies);

            for (Map.Entry<Hobby, Integer> h : hobbies.entrySet()) {
                hobbyList.add(h.getKey());
                userList.add(h.getValue());
            }

            assertEquals(3, hobbyList.size());
            for(Hobby h: hobbyList){
                if(h.getId() == 1){
                    assertEquals(2, h.getUsers().size());
                }
                if(h.getId() == 2){
                    assertEquals(1, h.getUsers().size());
                }
                if(h.getId() == 3){
                    assertEquals(1, h.getUsers().size());
                }
            }

    }
}