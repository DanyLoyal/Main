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
@Table(name ="hobby")
@Entity
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "wikiLink")
    private String link;

    @ManyToMany(mappedBy = "hobbies")

    private  Set<User>users = new HashSet<>();

    @ManyToMany

    @JoinTable(name = "hobby_hobby_info",
            joinColumns = @JoinColumn(name = "hobby_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_info_id")
    )
    private Set<HobbyInfo> hobbyInfos = new HashSet<>();

    public Hobby(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public void setUser(User user) {
        users.add(user);
    }

    public void setInterestsInfo(HobbyInfo hobbyInfo) {
        hobbyInfos.add(hobbyInfo);
        hobbyInfo.setInterest(this);
    }
}
