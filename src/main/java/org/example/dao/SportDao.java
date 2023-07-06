package org.example.dao;

import org.example.entites.Sport;
import org.example.types.RepositoryType;

import java.util.Collection;

public class SportDao extends BaseDao<Sport> {

    public SportDao() {
        super(RepositoryType.SPORT);
    }
    @Override
    public void persist(Collection<Sport> entities){
        entities.forEach(e -> cache.getCache().put(e.getIdentifier(), e));
        repository.persistMultipleEntites(entities);
    }
}
