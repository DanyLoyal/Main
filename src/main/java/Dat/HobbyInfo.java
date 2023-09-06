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

    @Enumerated(EnumType.STRING)
    @Column(name = "interestsType")
    private InterestsType interestsType;

    @ManyToMany(mappedBy = "hobbyInfos")
    private Set<Hobby> hobbies = new HashSet<>();

    public void setHobby(Hobby hobby) {
        hobbies.add(hobby);
    }

    public void setInterestsType(InterestsType type){
        this.interestsType = type;
    }

    public HobbyInfo(InterestsType interestsType) {
        this.interestsType = interestsType;
    }

    public void removeHobby(Hobby hobby) {
        hobbies.remove(hobby);
    }

    public boolean deleteHobbyInfo(){
        for(Hobby h: hobbies){
            h.removeHobbyInfo(this);
        }
        if(hobbies.size() == 0){
            return true;
        }
        return false;
    }


    public enum InterestsType {
        GENEREL,
        INDENDØRS,
        UDENDØRS,
        KONKURRENCE,
        OBSERVATION,
        SAMLER_HOBBYER,
        EDUCATIONAL_HOBBIES
    }


}
