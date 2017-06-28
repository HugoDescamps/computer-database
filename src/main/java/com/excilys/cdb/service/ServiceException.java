package com.excilys.cdb.service;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String s, Throwable t) {

	}

	public ServiceException(String s) {
		super(s);
	}

}
