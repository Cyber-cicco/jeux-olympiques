package org.example.entites;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelleFR;
    private String libelleEN;
    @OneToMany(mappedBy = "sport")
    private Set<Epreuve> epreuves;

}
