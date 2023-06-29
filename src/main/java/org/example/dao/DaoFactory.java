package org.example.dao;

public class DaoFactory {
    private static AthleteDao athleteDao;
    private static EpreuveDao epreuveDao;
    private static OrganisationDao organisationDao;
    private static ResultatDao resultatDao;
    private static SessionDao sessionDao;
    private static SportDao sportDao;

    public static AthleteDao getAthleteDao(){
        if(athleteDao != null) return athleteDao;
        athleteDao = new AthleteDao();
        return athleteDao;
    }
    public static EpreuveDao getEpreuveDao(){
        if(epreuveDao != null) return epreuveDao;
        epreuveDao = new EpreuveDao();
        return epreuveDao;
    }
    public static OrganisationDao getOrganisationDao(){
        if(organisationDao != null) return organisationDao;
        organisationDao = new OrganisationDao();
        return organisationDao;
    }
    public static ResultatDao getResultatDao(){
        if(resultatDao != null) return resultatDao;
        resultatDao = new ResultatDao();
        return resultatDao;
    }
    public static SessionDao getSessionDao(){
        if(sessionDao != null) return sessionDao;
        sessionDao =  new SessionDao();
        return sessionDao;
    }
    public static SportDao getSportDao(){
        if(sportDao != null) return sportDao;
        sportDao =  new SportDao();
        return sportDao;
    }
    /**
     * Méthode permettant de fermer
     * les connexions à la base de données.
     * */
    public static void closeDaos(){
        if(athleteDao != null) athleteDao.close();
        if(epreuveDao != null) epreuveDao.close();
        if(resultatDao != null) resultatDao.close();
        if(sessionDao != null) sessionDao.close();
        if(sportDao != null) sportDao.close();
        if(organisationDao != null) organisationDao.close();
    }

}
