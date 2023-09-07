package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name ="User.findAll", query = "SELECT g FROM User g"),
        @NamedQuery(name = "User.findByZip", query = "SELECT u FROM User u WHERE u.userInfo.address.zip.cityName = :cityName"),
        @NamedQuery(name="User.findUsersByHobby", query = "SELECT h FROM User h WHERE Hobby.id =: id")
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "firstname")
    private String firstname;


    @Column(name = "lastname")
    private String lastname;


    @OneToOne (mappedBy = "user", cascade = CascadeType.PERSIST)
    private  UserInfo userInfo;

    @ManyToMany
    //@JoinColumn (name = "user_id")

    @JoinTable(name = "user_hobby",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )

    private Set<Hobby> hobbies = new HashSet<>();

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
        userInfo.setUser(this);
    }

    public void setInterest(Hobby hobby) {
        hobbies.add(hobby);
        hobby.setUser(this);
    }






}
