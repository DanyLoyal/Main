package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Type;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "phonenumber")
@Entity
@NamedQueries({
        @NamedQuery(name = "Phonenumber.removeByUserID", query = "DELETE FROM Phonenumber p WHERE p.userInfo.id = :id")
})
public class Phonenumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phonenumber")
    private int phonenumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PhoneType phoneType;
    @ManyToOne
    private UserInfo userInfo;

    public Phonenumber(int phonenumber, PhoneType phoneType) {
        this.phonenumber = phonenumber;
        this.phoneType = phoneType;
    }

    public void setUserinfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    enum PhoneType {
        MOBILE,
        INTERNET_PHONE,
        LANDLINE;
    }




}
