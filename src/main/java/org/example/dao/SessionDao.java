package org.example.dao;

import org.example.entites.Session;
import org.example.types.RepositoryType;

public class SessionDao extends BaseDao<Session> {
    public SessionDao() {
        super(RepositoryType.SESSION);
    }
}
