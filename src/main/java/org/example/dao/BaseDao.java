package org.example.dao;


import lombok.SneakyThrows;
import org.example.config.DataBaseConfig;
import org.example.types.RepositoryType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> {

    private Cache<T> cache;
    private Repository repository;
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
            args.put("identifier" + 1, fields.get(i));
        }
        return (T) repository.findByField(query, args).get(0);
    }

    public void persist(T entity){
        repository.persistEntity(entity);
    }

    @SneakyThrows
    public void close(){
        repository.close();
    }
}
