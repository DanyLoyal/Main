package Dat;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Table()
@Entity
public class Interests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    public Interests(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
