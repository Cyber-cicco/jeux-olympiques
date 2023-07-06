package org.example.parser;

import org.example.config.DataBaseConfig;
import org.example.dao.*;
import org.example.entites.*;
import org.example.types.Medaille;
import org.example.types.Season;
import org.example.types.Sex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvenementParser extends Parser {

    private AthleteDao athleteDao = DaoFactory.getAthleteDao();
    private EpreuveDao epreuveDao = DaoFactory.getEpreuveDao();
    private OrganisationDao organisationDao = DaoFactory.getOrganisationDao();
    private ResultatDao resultatDao = DaoFactory.getResultatDao();
    private SessionDao sessionDao = DaoFactory.getSessionDao();
    private SportDao sportDao = DaoFactory.getSportDao();
    private Set<Athlete> athletes = new HashSet<>();
    private Set<Epreuve> epreuves = new HashSet<>();
    private Set<Session> sessions = new HashSet<>();
    private Set<Resultat> resultats = new HashSet<>();
    private Set<Sport> sports = new HashSet<>();
    private Set<Organisation> organisations = new HashSet<>();
    private Athlete athlete;
    private Epreuve epreuve;
    private Session session;
    private Resultat resultat;
    private Sport sport;
    private Organisation organisation;
    private boolean hasToPersist;

    private void initializeParsing(String text, int lineNumber){
        this.text = text;
        position = 0;
        hasToPersist = lineNumber % DataBaseConfig.MAX_PERSISTENCE == 0;
        session = new Session();
        athlete = new Athlete();
        epreuve = new Epreuve();
        resultat = new Resultat();
    }

    private void parseNomAthlete(){
        var nomAthlete = getBasicFieldString();
        athlete.setName(nomAthlete);
    }

    private void parseSexeAthelete(){
        if(text.charAt(position) == 'M'){
            athlete.setSexe(Sex.MALE);
        } else {
            athlete.setSexe(Sex.FEMALE);
        }
        nextChar();
    }

    private void parseAgeAthlete(){
        String ageAthlete = getBasicFieldString();
        if(!ageAthlete.equals("NA")) resultat.setAge(Integer.parseInt(ageAthlete));
    }

    private void skipId(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }
    private void parseHeightAthlete() {
        String height = getBasicFieldString();
        if(!height.equals("NA")) athlete.setHeight(Integer.parseInt(height));
        resultat.setAthlete(athleteDao.setNeededPersistence(athlete, athletes));
        if(hasToPersist){
            athleteDao.persist(athletes);
        }
    }

    private void parseWeightForSession() {
        var weight = getBasicFieldString();
        if(!weight.equals("NA")) resultat.setPoids(Double.parseDouble(weight));
    }

    private void parseNOC(){
        String codeNOC = getBasicFieldString();
        String query = "select o from Organisation o where o.codeCIO = :identifier0";
        if(codeNOC.equals("LIB")) codeNOC = "LBN";
        if(!codeNOC.equals("IOA") && !codeNOC.equals("UNK")) organisation = organisationDao.extraireParId(codeNOC, query, List.of(codeNOC));
        resultat.setOrganisation(organisation);
    }

    private void ignoreTeam(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }

    private void ignoreGames(){
        while (text.charAt(position) != CSV_SEPARATOR){
            nextChar();
        }
    }

    private void parseYear() {
        String year = getBasicFieldString();
        session.setYear(Integer.parseInt(year));
    }

    private void parseSeasons(){
        var season = getBasicFieldString();
        if (season.equals("Summer")) {
            session.setSeason(Season.SUMMER);
        } else {
            session.setSeason(Season.WINTER);
        }
    }

    private void parseCity(){
        String city = getBasicFieldString();
        session.setCity(city);
        resultat.setSession(sessionDao.setNeededPersistence(session, sessions));
        if(hasToPersist) sessionDao.persist(sessions);
    }

    private void getSport(){
        String nomSport = getBasicFieldString();
        String query = "select s from Sport s where s.libelleEN = :identifier0";
        sport = sportDao.extraireParId(nomSport, query, List.of(nomSport));
        epreuve.setSport(sport);
    }

    private void parseEpreuve(){
        String nomEpreuve = getBasicFieldString();
        nomEpreuve = nomEpreuve.replaceFirst(sport.getLibelleEN(), "").trim();
        epreuve.setEventEN(nomEpreuve);
        epreuveDao.changeEntityIfSport(epreuve, resultat);
    }

    private void parseResultat(){
        String nom = text.substring(position);
        switch (nom){
            case "Gold" -> resultat.setMedaille(Medaille.OR);
            case "Silver" -> resultat.setMedaille(Medaille.ARGENT);
            case "Bronze" -> resultat.setMedaille(Medaille.BRONZE);
        }
        resultats.add(resultat);
    }

    private void endParsing(){
        if(hasToPersist){
            System.out.println(text);
            resultatDao.persist(resultats);
            organisations.clear();
            athletes.clear();
            sessions.clear();
            resultats.clear();
        }
    }

    public void parseLine(String text, int lineNumber){
        initializeParsing(text, lineNumber);
        skipId();
        nextChar();
        parseNomAthlete();
        nextChar();
        parseSexeAthelete();
        nextChar();
        parseAgeAthlete();
        nextChar();
        parseHeightAthlete();
        nextChar();
        parseWeightForSession();
        nextChar();
        ignoreTeam();
        nextChar();
        parseNOC();
        nextChar();
        ignoreGames();
        nextChar();
        parseYear();
        nextChar();
        parseSeasons();
        nextChar();
        parseCity();
        nextChar();
        getSport();
        nextChar();
        parseEpreuve();
        nextChar();
        parseResultat();
        endParsing();
    }
}
