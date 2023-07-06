package org.example.entites;

import jakarta.persistence.*;
import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;
import org.example.types.Medaille;

@Entity
public class Resultat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_session")
    private Session session;
    @ManyToOne
    @JoinColumn(name = "id_athlete")
    private Athlete athlete;
    @ManyToOne
    @JoinColumn(name = "id_organisation")
    private Organisation organisation;
    @ManyToOne
    @JoinColumn(name = "id_epreuve")
    private Epreuve epreuve;
    private Double poids;
    private Integer age;

    @Enumerated
    private Medaille medaille;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Epreuve getEpreuve() {
        return epreuve;
    }

    public void setEpreuve(Epreuve epreuve) {
        this.epreuve = epreuve;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Integer getAge() {
        return age;
    }

    public Medaille getMedaille() {
        return medaille;
    }

    public void setMedaille(Medaille medaille) {
        this.medaille = medaille;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getIdentifier() {
        return id.toString();
    }
}
