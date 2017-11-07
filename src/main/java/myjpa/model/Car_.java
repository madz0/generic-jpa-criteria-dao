package myjpa.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( Car.class )
public class Car_ {

	public static volatile SingularAttribute<Car, Long> id;
	public static volatile SingularAttribute<Car, String> name;
	public static volatile SingularAttribute<Car, String> model;
	public static volatile SingularAttribute<Car, Person> owner;
}
