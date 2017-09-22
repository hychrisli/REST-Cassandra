package cmpe.restapi.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 84963630719145527L;

	@Id
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	public Employee(){}
	
	public Employee(Employee that){
		this.id = that.id;
		this.firstname = that.firstname;
		this.lastname = that.lastname;
	}
	
	public Employee(Long id, String firstname, String lastname){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
