package Dat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "address")
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "street_name")
    private String streetName;


    @Column(name = "number")
    private String number;

    public Address(String streetName, String number) {
        this.streetName = streetName;
        this.number = number;
    }
}