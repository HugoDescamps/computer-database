package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {

	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {
		super();
	}

	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "\tComputer id : " + id + "\tComputer name : " + name + "\tIntroduced date : " + introducedDate + "\tDiscontinued date : "
				+ discontinuedDate + "\tOwning company : " + companyName;
	}

}
