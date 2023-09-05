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
@Table(name = "zip")
@Entity
public class Zip {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "zip")
    private int zip;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "region")
    private String region;
    @Column(name = "municipality")
    private String municipality;

    public Zip(int zip, String cityName, String region, String municipality) {
        this.zip = zip;
        this.cityName = cityName;
        this.region = region;
        this.municipality = municipality;
    }

}
