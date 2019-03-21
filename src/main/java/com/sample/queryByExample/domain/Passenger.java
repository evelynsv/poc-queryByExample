package com.sample.queryByExample.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * @author EvelynVieira
 *
 */
@Entity
public class Passenger {
	
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;
	
	@Basic(optional = false)
    @Column(nullable = false)
	private String firstName;	
	
	@Basic(optional = false)
    @Column(nullable = false)
	private String lastName;
	
	@Basic(optional = false)
    @Column(nullable = false)
	private Integer seatNumber;

	public Passenger() {}

	public Passenger(Long id, String firstName, String lastName, Integer seatNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.seatNumber = seatNumber;
	}

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

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + seatNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (seatNumber != other.seatNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", seatNumber="
				+ seatNumber + "]";
	}

	 static Passenger from(Long id, String firstName, String lastName, Integer seatNumber) {
	        return new Passenger(id,firstName, lastName, seatNumber);
	    }
	
}
