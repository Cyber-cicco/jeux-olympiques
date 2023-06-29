package org.example.dao;

import jakarta.persistence.*;
import org.example.types.RepositoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Repository implements AutoCloseable {
    private final EntityManager em;
    /**EntityManagerFactory : emf*/
    private final EntityManagerFactory emf;
    /**Chaine permettant de faire de l'extraction de la base*/
    private final String SELECT = "select e from %s e";
    /**
     * Type du repository, permettant d'offrir un repository, et donc un
     * emf par DAO
     * */
    private final RepositoryType repositoryType;
    /**String de sélection avec une condition*/
    private final String SELECT_WITH_CONDITION = "select e from %s e where %s = %s";
    /**Liste des différents repository instanciés*/
    private static final List<Repository> generalRepositores = new ArrayList<>();

    /**Nom de la base de données*/
    private final String dataBaseName;
    /**
     * Constructeur
     * Crée l'em, l'emf, et assigne le nom de la base et le type de repo
     * associé à l'objet
     * */
    private Repository(String dataBaseName, RepositoryType repositoryType){
        emf = Persistence.createEntityManagerFactory(dataBaseName);
        em = emf.createEntityManager();
        this.repositoryType = repositoryType;
        this.dataBaseName = dataBaseName;
    }

    /**
     * Variation d'une méthode type Singleton, qui renvoie des repository
     * différents en fonction du nom de la base de données et du tupe du
     * repository
     * */
    public static Repository getInstance(String dataBaseName, RepositoryType repositoryType){
        List<Repository> repository = generalRepositores.stream()
                .filter(generalRepository -> Objects.equals(generalRepository.getDataBaseName(), dataBaseName) && generalRepository.repositoryType == repositoryType).toList();
        if(repository.size() > 0){
            return repository.get(0);
        }
        var newRepo = new Repository(dataBaseName, repositoryType);
        generalRepositores.add(newRepo);
        return newRepo;
    }

    /**
     * Méthode ouvrant une nouvelle connexion et persistant de
     * multiples entités en une seule transaction
     * */
    public <T> void persistMultipleEntites(List<T> entites){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        for (T entite : entites) {
            em.persist(entite);
        }
        tr.commit();
        em.close();
    }

    /**Méthode ouvrant une nouvelle connexion pour persister une entité*/
    public <T> void persistEntityWithNewConnection(T entity){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        em.persist(entity);
        tr.commit();
        em.close();
    }

    /**
     * Méthode utilisant la connexion ouverte à la construction du repo pour
     * persiter une entité
     * @param entity : entité
     * */
    public <T> void persistEntity(T entity){
        doTransaction(entity, (em::persist));
    }

    /**Méthode permettant d'exécuter une requête dans une transaction*/
    private <T> void doTransaction(T entity, Consumer<T> transaction){
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        transaction.accept(entity);
        tr.commit();
        em.close();
    }

    /**
     * Méthode permettant d'extraire toutes les entités du nom précisé en paramètre
     * @param entityName : le nom de l'entité
     * */
    public <T> List<T> getAll(String entityName){
        Query query =  em.createQuery(String.format(SELECT, entityName));
        return  query.getResultList();
    }

    /**
     * Méthode permettant de sélectionner une entité par la valeur d'un field
     * @param args : une map contenant les arguments de la requête JPQL et leur valeur;
     * @param statement : la requête JPQL
     * */
    public <T> List<T> findByField(String statement, Map<String, String> args){
        Query query =  em.createQuery(statement);
        for(String key : args.keySet()){
            query.setParameter(key, args.get(key));
        }
        return query.getResultList();
    }

    /**
     * Méthode permettant de changer une entité
     * @param changeEntity : Consumer permettant de changer les fields de l'entité
     * @param entity : l'entité à changer
     * @param id : l'id de l'entité à changer
     * */
    public <T> void changeEntity(Consumer<T> changeEntity, T entity, int id){
        T managedEntity = (T) em.find(entity.getClass(), id);
        doTransaction(managedEntity, changeEntity);
    }

    /**
     * Permet d'exécuter une requête
     * @param statement : la requête
     * @param maxResults : le nombre maximum de résultats souhaités
     * @param arguments : les arguments de la requête
     * @return List d'entités
     * */
    public <T> List<T> executeQuery(String statement, Map<String, String> arguments, int maxResults){
        Query query = em.createQuery(statement);
        for(String key : arguments.keySet()){
            query.setParameter(key, arguments.get(key));
        }
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    /**
     * Permet de récupérer une entité
     * @param entity
     * @param id
     * @return l'entité trouvée en base
     * */
    public <T> T getOne(T entity, int id){
        return (T) em.find(entity.getClass(), id);
    }

    /**
     * Ferme l'em et l'emf
     * */
    @Override
    public void close() throws Exception {
        generalRepositores.remove(this);
        em.close();
        emf.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return ((Repository) o).getDataBaseName() == dataBaseName;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "repositoryType=" + repositoryType +
                ", dataBaseName='" + dataBaseName + '\'' +
                '}';
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

}
