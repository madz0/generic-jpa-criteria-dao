package myjpa.dal;

import java.util.List;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoImpl implements IDao {

	private final static Logger LOG = LoggerFactory.getLogger(DaoImpl.class);
	
	EntityManagerFactory factory = null;
	private EntityManager entityManager = null;
	
	public DaoImpl() throws Exception {
		
		this("PersistenceUnit");
	}
	
	public DaoImpl(String presistanceUnitName) throws Exception {

		try {
			
			factory = Persistence.createEntityManagerFactory(presistanceUnitName);
			entityManager = factory.createEntityManager();
			
		} catch (Exception e) {
			
			LOG.error("",  e);	
		}
	}
	
	@Override
	public <T> List<T> findAll() {
		
		@SuppressWarnings("unchecked")
		Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    	
    	
    	return find(persistentClass, persistentClass, (cb, root, q)->(q.select(root)));
	}
	
	@Override
	public <T> List<T> find(Class<T> entityClass, PredicateBuilderExpression<T> pb) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> q = cb.createQuery(entityClass);
	    Root<T> root = q.from(entityClass);
	    
	    CriteriaQuery<T> criteriaQuery = q.select(root);
	    criteriaQuery.where(pb.build(cb, root));
	    TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
	    return typedQuery.getResultList();
	}
	
	@Override
	public <T> List<T> find(Class<T> entity, CriteriaQueryBuilderExpression<T, T> cqbe) {
		
		return find(entity, entity, cqbe);
	}
	
	@Override
	public <T, X> List<X> find(Class<T> entityIn, Class<X> entityOut, CriteriaQueryBuilderExpression<T, X> cqbe) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<X> q = cb.createQuery(entityOut);
	    Root<T> root = q.from(entityIn);

	    TypedQuery<X> typedQuery = entityManager.createQuery(cqbe.build(cb, root, q));
	    return typedQuery.getResultList();
	}

	@Override
	public <T> boolean add(T entity) throws Exception {

		EntityTransaction transaction = entityManager.getTransaction();
    	
    	try {
    		
    		transaction.begin();
    		
    		entityManager.persist(entity);
    		
    		transaction.commit();
    		
    		return true;
    		
    	} catch (Exception e) {
    		
    		if (transaction.isActive()) {
    			
    			transaction.rollback();
    		}
    		
    		throw e;
    	}
	}

	@Override
	public <T> boolean update(T entity) throws Exception {

		EntityTransaction transaction = entityManager.getTransaction();
    	
    	try {
    		
    		transaction.begin();
    		
    		entityManager.merge(entity);
    		
    		transaction.commit();
    		
    		return true;
    		
    	} catch (Exception e) {
    		
    		if (transaction.isActive()) {
    			
    			transaction.rollback();
    		}
    		
    		throw e;
    	}
	}

	@Override
	public <T> boolean delete(T entity) throws Exception {

		EntityTransaction transaction = entityManager.getTransaction();
    	
    	try {
    		
    		transaction.begin();
    		
    		entityManager.remove(entity);
    		
    		transaction.commit();
    		
    		return true;
    		
    	} catch (Exception e) {
    		
    		if (transaction.isActive()) {
    			
    			transaction.rollback();
    		}
    		
    		throw e;
    	}
	}

	@Override
	public void close() {
		
		try {
			
			if (entityManager != null) {
				
				entityManager.close();
			}
			
			if (factory != null) {
				
				factory.close();
			}
			
		} catch (Exception e) {}
	}
}
