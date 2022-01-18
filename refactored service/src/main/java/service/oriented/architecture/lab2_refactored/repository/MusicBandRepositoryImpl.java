package service.oriented.architecture.lab2_refactored.repository;

import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import service.oriented.architecture.lab2_refactored.dto.PagedMusicBandList;
import service.oriented.architecture.lab2_refactored.entity.MusicBand;
import service.oriented.architecture.lab2_refactored.enums.MusicGenre;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;
import service.oriented.architecture.lab2_refactored.utils.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MusicBandRepositoryImpl implements MusicBandRepositoryCustom {
    @PersistenceContext
    private Session session;
    @PersistenceContext
    private EntityManager em;

    private boolean existsById(Integer id) {
        org.hibernate.query.Query query = session.createQuery("SELECT 1 FROM MusicBand l WHERE l.id = ?1");
        query.setParameter(1, id);
        return (query.uniqueResult() != null);
    }

    public MusicBand findById(Integer id) {
        if (existsById(id)) {
            Query query = em.createQuery("SELECT c FROM MusicBand c WHERE c.id = ?1", MusicBand.class);
            return (MusicBand) query.setParameter(1, id).getSingleResult();
        } else
            throw new EntityIsNotValidException("musicBand with id = " + id + " does not exist");
    }


    public void clearEntityManager() {
        em.clear();
    }

    private List<MusicBand> findAll(Integer perPage, Integer curPage, CriteriaQuery<MusicBand> select) {
        if (perPage != null && curPage != null) {
            int pageNumber = curPage;
            int pageSize = perPage;
            TypedQuery<MusicBand> typedQuery = em.createQuery(select);
            typedQuery.setFirstResult((pageNumber - 1) * pageSize);
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        } else
            return findAll(select);
    }

    private List<MusicBand> findAll(CriteriaQuery<MusicBand> select) {
        TypedQuery<MusicBand> typedQuery = em.createQuery(select);
        return typedQuery.getResultList();
    }

    private Long getOverallCount(CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(MusicBand.class)));
        em.createQuery(countQuery);
        if (predicates.size() > 0)
            countQuery.where(predicates.toArray(new Predicate[]{}));
        return em.createQuery(countQuery).getSingleResult();

    }

    private ArrayList<Predicate> getPredicatesList(String filterBy, CriteriaBuilder criteriaBuilder, Root<MusicBand> from) throws IOException {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (filterBy != null) {
            List<String> notParsedFilters = new ArrayList<>(Arrays.asList(filterBy.split(";")));
            for (String filterString : notParsedFilters) {
                List<String> filter = new ArrayList<>(Arrays.asList(filterString.split(",")));
                switch (filter.get(0)) {
                    case ("id"):
                        if (filter.size() < 3) throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("id"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("name"):
                        if (filter.size() < 2) throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;
                    case ("singles"):
                        if (filter.size() < 3) throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("singlesCount"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("singlesCount"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("numberOfParticipants"):
                        if (filter.size() < 3) throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("numberOfParticipants"), Integer.parseInt(filter.get(1))));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("numberOfParticipants"), Integer.parseInt(filter.get(2))));
                        break;
                    case ("genre"):
                        if (filter.size() < 2) throw new EntityIsNotValidException("number of arguments less than required");
                        predicates.add(criteriaBuilder.equal(from.get("genre"), MusicGenre.valueOf(filter.get(1))));
                        break;
                    case ("date"):
                        if (filter.size() < 3) throw new EntityIsNotValidException("number of arguments less than required");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y");
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("creationDate"), LocalDate.parse(filter.get(1), formatter)));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("creationDate"), LocalDate.parse(filter.get(2), formatter)));
                        break;
                    case ("coordinate"):
                        if (filter.size() < 5) throw new EntityIsNotValidException("number of arguments less than required");
                        Predicate x = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(1))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("x"), Double.parseDouble(filter.get(2))));
                        Predicate y = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(3))),
                                criteriaBuilder.lessThanOrEqualTo(from.get("coordinates").get("y"), Double.parseDouble(filter.get(4))));
                        predicates.add(criteriaBuilder.and(x, y));
                        break;
                    case ("bestAlbum"):
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(from.get("bestAlbum").get("name")),
                                filter.get(1).toUpperCase() + "%"));
                        break;
                }
            }
        }

        return predicates;
    }

    private List<Order> getOrderList(String sortBy, CriteriaBuilder criteriaBuilder, Root<MusicBand> from) {
        List<Order> orderList = new ArrayList();
        if (sortBy != null) {
            List<String> criteria = new ArrayList<>(Arrays.asList(sortBy.split(";")));
            for (String criterion : criteria) {
                switch (criterion) {
                    case ("id"):
                        orderList.add(criteriaBuilder.asc(from.get("id")));
                        break;
                    case ("name"):
                        orderList.add(criteriaBuilder.asc(from.get("name")));
                        break;
                    case ("numberOfParticipants"):
                        orderList.add(criteriaBuilder.asc(from.get("numberOfParticipants")));
                        break;
                    case ("singles"):
                        orderList.add(criteriaBuilder.asc(from.get("singlesCount")));
                        break;
                    case ("genre"):
                        orderList.add(criteriaBuilder.asc(from.get("genre")));
                        break;
                    case ("date"):
                        orderList.add(criteriaBuilder.asc(from.get("creationDate")));
                        break;
                    case ("coordinate"):
                        orderList.add(criteriaBuilder.asc(from.get("coordinates").get("x")));
                        break;
                    case ("bestAlbum"):
                        orderList.add(criteriaBuilder.asc(from.get("bestAlbum").get("name")));
                        break;
                }
            }
        }
        return orderList;
    }

    public void create(MusicBand musicBand) {
        em.getTransaction().begin();
        em.persist(musicBand);
        em.getTransaction().commit();
        em.clear();
    }

    public void update(MusicBand musicBand) {
        em.getTransaction().begin();
        em.merge(musicBand);
        em.getTransaction().commit();
        em.clear();
    }

    public void delete(MusicBand musicBand) {
        em.getTransaction().begin();
        em.remove(musicBand);
        em.getTransaction().commit();
        em.clear();
    }

    @Override
    public PagedMusicBandList findAll(Integer perPage, Integer curPage, String sortBy, String filterBy) throws IOException {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<MusicBand> criteriaQuery = criteriaBuilder.createQuery(MusicBand.class);
        Root<MusicBand> from = criteriaQuery.from(MusicBand.class);
        CriteriaQuery<MusicBand> select = criteriaQuery.select(from);

        List<Order> orderList = getOrderList(sortBy, criteriaBuilder, from);
        if (!orderList.isEmpty())
            criteriaQuery.orderBy(orderList);

        ArrayList<Predicate> predicates = getPredicatesList(filterBy, criteriaBuilder, from);
        if (!predicates.isEmpty())
            select.where(predicates.toArray(new Predicate[]{}));

        PagedMusicBandList pagedMusicBandList = new PagedMusicBandList();

        pagedMusicBandList.setCount(getOverallCount(criteriaBuilder, predicates));
        pagedMusicBandList.setMusicBandList(findAll(perPage, curPage, select));

        return pagedMusicBandList;
    }
}
