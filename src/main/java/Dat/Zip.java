package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "zipcode")
@NamedQueries({
        @NamedQuery(name = "Zip.findAll", query = "select z from Zip z"),
        @NamedQuery(name = "Zip.findByCityName", query = "SELECT z from Zip z WHERE cityName = :cityName")
})
@Entity
public class Zip {

    @Id
    @Column(name = "zip")
    private int zip;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "region_name")
    private String region;
    @Column(name = "municipality_name")
    private String municipality;
    @OneToMany(mappedBy = "zip")
    private Set<Address> address = new HashSet<>();

    public Zip(int zip, String cityName, String region, String municipality) {
        this.zip = zip;
        this.cityName = cityName;
        this.region = region;
        this.municipality = municipality;
    }






}
