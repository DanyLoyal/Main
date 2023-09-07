package Dat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Table(name = "address")
@Entity
@NamedQueries({
        //@NamedQuery(name = "Address.deleteByUserID", query = "DELETE Address a WHERE Address.userInfo.id = :id")
})
public class Address {

    @Id
    private int id;

    @Column(name = "street_name")
    private String streetName;


    @Column(name = "number")
    private String number;

    @ManyToOne
    private Zip zip;

    @MapsId
    @OneToOne
    private UserInfo userInfo;

    public Address(String streetName, Zip zip, String number) {
        this.streetName = streetName;
        this.number = number;
        this.zip = zip;
    }


    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}