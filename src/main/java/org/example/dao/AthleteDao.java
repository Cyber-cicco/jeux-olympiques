package org.example.dao;

import org.example.entites.Athlete;
import org.example.types.RepositoryType;

public class AthleteDao extends BaseDao<Athlete> {
    public AthleteDao() {
        super(RepositoryType.ATHLETE);
    }
}
