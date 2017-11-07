package myjpa.myjpa_sample;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import myjpa.dal.DaoImpl;
import myjpa.dal.IDao;
import myjpa.model.Car;
import myjpa.model.Car_;
import myjpa.model.Person;
import myjpa.model.Person_;

/**
 * Hello world!
 *
 */
public class App {
	
	private static Logger LOGGER;
	
    public static void main( String[] args ) {
    	
    	System.setProperty("log4j.configuration", "file:///mnt/backup/"+
    	"MarsWorkplace/Myjpa-sample/src/main/resource/log4j.properties");
    	
    	LOGGER = LoggerFactory.getLogger(App.class);
    	
        App app = new App();
        
        LOGGER.info("Start");
        app.run2();
    }
    
    @SuppressWarnings("unchecked")
	public void run2() {
    	
    	try {
    		
    		IDao dao = new DaoImpl();
    		List<Person> persons = null;
    		List<Car> cars = null;
    				
    		persons = dao.find(Person.class, (cb, root) -> (cb.equal(root.get("id") , "1")) );
    		
    		LOGGER.info("1)+++++++++++++++++++++++++++++{}", persons);
    		
    		persons = dao.find(Person.class, (cb, root, query) 
    				-> (query.where(cb.equal (root.get(Person_.id) , "1"))
    						.orderBy(cb.desc(root.get(Person_.id)))));

    		LOGGER.info("2)+++++++++++++++++++++++++++++{}", persons);
    		
    		persons = dao.find(Person.class, (cb, root, query) -> 
    		(query.select(root).orderBy(cb.desc(root.get(Person_.id)))));
    		
    		LOGGER.info("3)+++++++++++++++++++++++++++++{}", persons);
    		
    		persons = dao.find(Car.class, Person.class, (cb, root, query)->(query.select(root.join(Car_.owner))));
    		
    		LOGGER.info("4)+++++++++++++++++++++++++++++{}", persons);
    		
    		cars = dao.find(Car.class, (cb, root, query)->{
    			//Join fetch
    			root.fetch(Car_.owner);
    			return query.select(root);
    		});
    		
    		LOGGER.info("1)*****************************{}", cars);
    		
    		//Join fetch
    		cars = dao.find(Car.class, (cb, root, query)->
    		(query.where(cb.equal(((Join<Car, Person>)root.
    				fetch(Car_.owner)).get(Person_.id), 1))));
    		
    		dao.close();
    		LOGGER.info("2)*****************************{}", cars);
    	}
    	catch(Exception e) {
    		
    		LOGGER.error("", e);
    	}
    }
    
    public void run() {
    	
		EntityManagerFactory factory = null;
		EntityManager entityManager = null;
		
		try {
			
			factory = Persistence.createEntityManagerFactory("PersistenceUnit");
			entityManager = factory.createEntityManager();
			persistPerson(entityManager);

			getPersons(entityManager);

		} catch (Exception e) {
			
			LOGGER.error("",  e);
			
		} finally {
			
			if (entityManager != null) {
				
				entityManager.close();
			}
			
			if (factory != null) {
				
				factory.close();
			}
		}
	}
    
    private void persistPerson(EntityManager entityManager) {
    	
    	EntityTransaction transaction = entityManager.getTransaction();
    	
    	try {
    		
    		transaction.begin();
    		Person person = new Person();
    		person.setFirstName("Homer"+ (new Random()).nextInt());
    		person.setLastName("Simpson");
    		entityManager.persist(person);
    		transaction.commit();
    		
    	} catch (Exception e) {
    		
    		if (transaction.isActive()) {
    			
    			transaction.rollback();
    		}
    	}
    }
    
    private void getPersons(EntityManager entityManager) {
    	
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    	
    	CriteriaQuery<Person> q = cb.createQuery(Person.class);
    	
    	Root<Person> root = q.from(Person.class);
    	
    	q.select(root);
    	
    	TypedQuery<Person> query = entityManager.createQuery(q);
    	List<Person> results = query.getResultList();
    	
    	LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Persons {}", results);
    }
}
