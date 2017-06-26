package com.excilys.cdb.persistence;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyException(String s, Throwable t) {
		super(s, t);
	}

	public MyException(String s) {
		super(s);
	}

}
