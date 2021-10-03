package de.dtonal.payroll.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.dtonal.payroll.model.auth.User;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Employee {
	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String role;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	Employee() {
	}

	public Employee(String firstName, String lastName, String role, User owner) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.owner = owner;
	}

	public Employee(String name, String role, User user) {
		setName(name);
		this.role = role;
		this.owner = user;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}

	public String getRole() {
		return this.role;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Employee))
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.getName(), employee.getName())
				&& Objects.equals(this.role, employee.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.getName(), this.role);
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", id=" + id + ", lastName=" + lastName + ", owner=" + owner
				+ ", role=" + role + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
