package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
@Table(name = "user_info")
@Entity
public class UserInfo {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "age")
    private int age;

    public UserInfo(String email, String address, int age) {
        this.email = email;
        this.address = address;
        this.age = age;
    }
}
