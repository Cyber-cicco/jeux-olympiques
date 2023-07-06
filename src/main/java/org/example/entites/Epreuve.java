package org.example.entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Epreuve extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventEN;
    private String eventFR;

    @ManyToOne
    @JoinColumn(name = "id_sport")
    private Sport sport;
    @OneToMany(mappedBy = "epreuve")
    private List<Resultat> resultats;

    public String getEventEN() {
        return eventEN;
    }

    public void setEventEN(String eventEN) {
        this.eventEN = eventEN;
    }

    public String getEventFR() {
        return eventFR;
    }

    public void setEventFR(String eventFR) {
        this.eventFR = eventFR;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String getIdentifier() {
        return eventEN;
    }
}
