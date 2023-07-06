package org.example.entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Organisation extends BaseEntity{

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

    public String getCodeCIO() {
        return codeCIO;
    }

    public void setCodeCIO(String codeCIO) {
        this.codeCIO = codeCIO;
    }

    public String getNomPaysFR() {
        return nomPaysFR;
    }

    public void setNomPaysFR(String nomPaysFR) {
        this.nomPaysFR = nomPaysFR;
    }

    public String getNomPaysEN() {
        return nomPaysEN;
    }

    public void setNomPaysEN(String nomPaysEN) {
        this.nomPaysEN = nomPaysEN;
    }

    public String getCodeISOAlpha() {
        return codeISOAlpha;
    }

    public void setCodeISOAlpha(String codeISOAlpha) {
        this.codeISOAlpha = codeISOAlpha;
    }

    public Boolean getObselete() {
        return isObselete;
    }

    public void setObselete(Boolean obselete) {
        isObselete = obselete;
    }

    @Override
    public String getIdentifier() {
        return codeCIO;
    }
}
