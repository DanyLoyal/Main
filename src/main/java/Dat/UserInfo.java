package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Getter
@NoArgsConstructor
@Table(name = "user_info")
@Entity
public class UserInfo {

    @Id
    private int id;

    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private int age;


    @OneToOne (mappedBy = "userInfo", cascade = CascadeType.PERSIST)
    private Address address;

    @MapsId
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "userInfo")
    Set<Phonenumber> phonenumbers = new HashSet<>();

    public UserInfo(String email, int age) {
        this.email = email;
        this.age = age;
        phonenumbers = new HashSet<>();
    }



    public void setPhonenumber(Phonenumber phonenumber) {
        phonenumbers.add(phonenumber);
        phonenumber.setUserinfo(this);
    }

    public void setAddress(Address address){
        this.address = address;
        address.setUserInfo(this);
    }

    public void setEmail(String email){
        this.email = email;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
