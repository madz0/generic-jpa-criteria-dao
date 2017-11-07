package myjpa.model;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public class Person {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname", length = 100, nullable = false, unique = false)
	private String lastName;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE, mappedBy="owner")
	private Set<Car> cars;

	public Long getId() {
		
		return id;
	}

	public void setId(Long id) {
		
		this.id = id;
	}

	public String getFirstName() {
		
		return firstName;
	}

	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}

	public String getLastName() {
		
		return lastName;
	}

	public void setLastName(String lastName) {
		
		this.lastName = lastName;
	}
	
	public Set<Car> getCars() {
		
		return cars;
	}

	public void setCars(Set<Car> cars) {
		
		this.cars = cars;
	}

	@Override
	public String toString() {

		return String.format("(%d, %s, %s)",id, firstName, lastName);
	}
}