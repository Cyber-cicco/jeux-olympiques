package org.example.entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(3)")
    private String codeCIO;
    private String nomPaysFR;
    private String nomPaysEN;
    @Column(columnDefinition = "varchar(3)")
    private String codeISOAlpha;
    private Boolean isObselete;

    @OneToMany(mappedBy = "organisation")
    private List<Resultat> resultats;
}
