package org.example.dao;

import org.example.entites.Epreuve;
import org.example.entites.Resultat;
import org.example.types.RepositoryType;

import java.util.Collection;


public class EpreuveDao extends BaseDao<Epreuve> {
    public EpreuveDao() {
        super(RepositoryType.EPREUVE);
    }

    public void changeEntityIfSport(Epreuve epreuve, Resultat resultat){
        Epreuve cachedEpreuve = cache.getCache().get(epreuve.getIdentifier());
        if(epreuve.getSport() != null && cachedEpreuve.getSport() == null){
            String query = String.format("update Epreuve e set id_sport = %s where e.eventEN  = '%s'", epreuve.getSport().getId(), epreuve.getEventEN().replace("'", "\\'"));
            repository.executeNativeQuery(query);
            resultat.setEpreuve(cachedEpreuve);
        }
    }
    @Override
    public void persist(Collection<Epreuve> entities){
        entities.forEach(epreuve -> cache.getCache().put(epreuve.getIdentifier(), epreuve));
        repository.persistMultipleEntites(entities);
    }

}

