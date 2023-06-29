package org.example.dao;

import org.example.entites.Epreuve;
import org.example.types.RepositoryType;

public class EpreuveDao extends BaseDao<Epreuve> {
    public EpreuveDao() {
        super(RepositoryType.EPREUVE);
    }
}
