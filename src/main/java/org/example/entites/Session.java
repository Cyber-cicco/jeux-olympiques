package org.example.entites;

import jakarta.persistence.*;
import org.example.types.Season;

import java.util.List;

@Entity
public class Session extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    @Enumerated
    private Season season;
    private String city;
    @OneToMany(mappedBy = "session")
    private List<Resultat> resultats;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getIdentifier() {
        return year + season.toString();
    }
}
