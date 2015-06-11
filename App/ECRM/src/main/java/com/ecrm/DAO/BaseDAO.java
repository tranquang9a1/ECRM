package com.ecrm.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by QuangTV on 5/18/2015.
 */
@Repository
public abstract class BaseDAO<T, ID extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> persistentClass;

    public BaseDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public T find(ID id) {
        return entityManager.find(persistentClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("from " + persistentClass.getName()).getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void merge(T entity) {
        entityManager.merge(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(T entity) {
        entityManager.remove(entity);
    }

}
