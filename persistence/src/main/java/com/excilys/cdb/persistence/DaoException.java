package com.excilys.cdb.persistence;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DaoException(String s, Throwable t) {
		
	}

	public DaoException(String s) {
		super(s);
	}

}
