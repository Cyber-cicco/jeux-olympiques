package org.example.dao;

import org.example.entites.Organisation;
import org.example.types.RepositoryType;

public class OrganisationDao extends BaseDao<Organisation> {
    public OrganisationDao() {
        super(RepositoryType.ORGANISATION);
    }
}
