package service.oriented.architecture.lab2_refactored.repository;

import org.hibernate.Session;
import service.oriented.architecture.lab2_refactored.entity.Album;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;
import service.oriented.architecture.lab2_refactored.utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AlbumRepositoryImpl {
    @PersistenceContext
    private Session session;
    @PersistenceContext
    private EntityManager em;

    public Album findById(Integer id) {
        if (existsById(id)){
            Query query = em.createQuery("SELECT c FROM Album c WHERE c.id = ?1", Album.class);
            return (Album) query.setParameter(1, id).getSingleResult();
        }else
            throw new EntityIsNotValidException("album with id = " + id + " does not exist");

    }

    private boolean existsById(Integer id) {
        org.hibernate.query.Query query = session.createQuery("SELECT 1 FROM Album l WHERE l.id = ?1");
        query.setParameter(1, id);
        return (query.uniqueResult() != null);
    }

    public List<Album> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Album> criteriaQuery = criteriaBuilder.createQuery(Album.class);
        Root<Album> from = criteriaQuery.from(Album.class);
        CriteriaQuery<Album> select = criteriaQuery.select(from);
        TypedQuery<Album> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    public void create(Album album) {
        em.getTransaction().begin();
        em.persist(album);
        em.getTransaction().commit();
        em.clear();
    }

    public void update(Album album) {
        em.getTransaction().begin();
        em.merge(album);
        em.getTransaction().commit();
        em.clear();
    }

}
