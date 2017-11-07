/**
 * 
 */
package myjpa.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author mohamadz
 *
 */

@Entity
@Table(name = "Car")
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public class Car {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name="id_person", columnDefinition="BIGINT")
	private Person owner;
	
	@Column(name="name")
	private String name;
	
	@Column(name="model")
	private String model;

	public Long getId() {
		
		return id;
	}

	public void setId(Long id) {
		
		this.id = id;
	}
	
	public Person getOwner() {
		
		return owner;
	}

	public void setOwner(Person owner) {
		
		this.owner = owner;
	}

	public String getName() {
		
		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public String getModel() {
		
		return model;
	}

	public void setModel(String model) {
		
		this.model = model;
	}
	
	@Override
	public String toString() {

		return String.format("(%d, %s, %s, %s)", id, name, model, owner);
	}
}
