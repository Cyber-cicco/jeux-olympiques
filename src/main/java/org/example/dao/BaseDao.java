package org.example.dao;


import lombok.SneakyThrows;
import org.example.config.DataBaseConfig;
import org.example.entites.BaseEntity;
import org.example.entites.Session;
import org.example.types.RepositoryType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T extends BaseEntity> {

    protected Cache<T> cache;
    protected Repository repository;
    public BaseDao(RepositoryType type) {
        this.cache = new Cache<>();
        repository = Repository.getInstance(DataBaseConfig.DATABASE_NAME, type);
    }

    public T extraireParId(String identifier, String query, List<String> fields){
        if(cache.getCache().containsKey(identifier)){
            return cache.getCache().get(identifier);
        }
        Map<String, String> args = new HashMap<>();
        for(int i = 0; i < fields.size(); i++){
            args.put("identifier" + i, fields.get(i));
        }
        List<T> entities = repository.findByField(query, args);
        if(entities.size() < 1) entities.add(null);
        return entities.get(0);
    }

    public void persist(Collection<T> entities){
        repository.persistMultipleEntites(entities);
    }

    public T setNeededPersistence(T entity, Collection<T> entites){
        boolean containsKey = cache.getCache().containsKey(entity.getIdentifier());
        if(!containsKey){
            cache.getCache().put(entity.getIdentifier(), entity);
            entites.add(entity);
            return entity;
        }
        return cache.getCache().get(entity.getIdentifier());
    }
    @SneakyThrows
    public void close(){
        repository.close();
    }
}
