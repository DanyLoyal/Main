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
public class Phonenumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phonenumber")
    private int phonenumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PhoneType phoneType;

    public Phonenumber(int phonenumber, PhoneType phoneType) {
        this.phonenumber = phonenumber;
        this.phoneType = phoneType;
    }

    enum PhoneType {
        MOBILE,
        INTERNET_PHONE,
        LANDLINE;
    }


}
