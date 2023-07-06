package org.example.entites;

import jakarta.persistence.*;
import org.example.dao.BaseDao;

import java.util.List;
import java.util.Set;

@Entity
public class Sport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelleFR;
    private String libelleEN;
    @OneToMany(mappedBy = "sport")
    private Set<Epreuve> epreuves;

    public String getLibelleFR() {
        return libelleFR;
    }

    public void setLibelleFR(String libelleFR) {
        this.libelleFR = libelleFR;
    }

    public String getLibelleEN() {
        return libelleEN;
    }

    public void setLibelleEN(String libelleEN) {
        this.libelleEN = libelleEN;
    }

    @Override
    public String getIdentifier() {
        return libelleEN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "id=" + id +
                ", libelleFR='" + libelleFR + '\'' +
                ", libelleEN='" + libelleEN + '\'' +
                ", epreuves=" + epreuves +
                '}';
    }
}
