package org.example.entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Epreuve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String event;

    @ManyToOne
    @JoinColumn(name = "id_sport")
    private Sport sport;
    @OneToMany(mappedBy = "epreuve")
    private List<Resultat> resultats;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
