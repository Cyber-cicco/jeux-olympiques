package org.example.dao;

import org.example.entites.Resultat;
import org.example.types.RepositoryType;

public class ResultatDao extends BaseDao<Resultat> {
    public ResultatDao() {
        super(RepositoryType.RESULTAT);
    }
}
