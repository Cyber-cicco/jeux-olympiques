package org.example.dao;

import org.example.entites.Sport;
import org.example.types.RepositoryType;

public class SportDao extends BaseDao<Sport> {

    public SportDao() {
        super(RepositoryType.SPORT);
    }
}
