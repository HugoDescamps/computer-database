package com.excilys.cdb.core;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "introduced")
	private LocalDate introduced;

	@Column(name = "discontinued")
	private LocalDate discontinued;

	@ManyToOne
	private Company company;

	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Computer() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		if (id > 0) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("Negative id");
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String toString() {
		long id = this.getId();
		String name = this.getName();
		String introducedDate = null;
		String discontinuedDate = null;
		String companyName = null;

		if (this.getIntroduced() != null) {
			introducedDate = this.getIntroduced().toString();
		}

		if (this.getDiscontinued() != null) {
			discontinuedDate = this.getDiscontinued().toString();
		}

		if (this.getCompany() != null) {
			companyName = this.getCompany().getName();

		}
		return "\tComputer id : " + id + "\tComputer name : " + name + "\tIntroduced date : " + introducedDate
				+ "\tDiscontinued date : " + discontinuedDate + "\tOwning company : " + companyName;
	}

}
