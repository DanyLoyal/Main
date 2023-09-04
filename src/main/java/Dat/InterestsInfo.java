package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
@Table(name="interests_info")
@Entity
public class InterestsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

     @Column(name="type")
    private InterestsType interestsType;

    public InterestsInfo(InterestsType interestsType) {
        this.interestsType = interestsType;
    }

    enum InterestsType{
         GENEREL,
         INDENDØRS,
         UDENDØRS,
         KONKURRENCE,
         OBSERVATION,
         SAMLER_HOBBYER,
         EDUCATIONAL_HOBBIES;
     }



}
