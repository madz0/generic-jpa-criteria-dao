```
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
```