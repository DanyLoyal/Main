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
@Table(name = "hobby_info")
@Entity
public class HobbyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private InterestsType interestsType;

    @ManyToMany(mappedBy = "hobbyInfos")
    private Set<Hobby> hobbies = new HashSet<>();

    public void setInterest(Hobby hobby) {
        hobbies.add(hobby);
    }

    public HobbyInfo(InterestsType interestsType) {
        this.interestsType = interestsType;
    }

    enum InterestsType {
        GENEREL,
        INDENDØRS,
        UDENDØRS,
        KONKURRENCE,
        OBSERVATION,
        SAMLER_HOBBYER,
        EDUCATIONAL_HOBBIES;
    }


}
