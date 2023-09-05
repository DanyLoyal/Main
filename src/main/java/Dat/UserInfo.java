package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor
@ToString
@Table(name = "user_info")
@Entity
public class UserInfo {

    @Id
    private int id;

    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;


    @OneToOne (mappedBy = "userInfo")
    private Address address;

    @MapsId
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "userInfo")
    Set<Phonenumber> phonenumbers = new HashSet<>();

    public UserInfo(String email, Address address, int age) {
        this.email = email;
        this.address = address;
        this.age = age;
        phonenumbers = new HashSet<>();
    }



    public void setPhonenumber(Phonenumber phonenumber) {
        phonenumbers.add(phonenumber);
        phonenumber.setUserinfo(this);
    }



}
