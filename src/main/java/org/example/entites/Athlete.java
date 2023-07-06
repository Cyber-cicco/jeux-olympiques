package org.example.entites;


import jakarta.persistence.*;
import org.example.types.Sex;

import java.util.List;

@Entity
public class Athlete extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated
    private Sex sex;
    private Integer height;

    @OneToMany(mappedBy = "athlete")
    private List<Resultat> resultats;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setSexe(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String getIdentifier() {
        return name;
    }

    public String getName() {
        return name;
    }
}
