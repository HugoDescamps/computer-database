package com.excilys.cdb.persistence;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyException(String errorMsg) {
		System.err.println(errorMsg+"\n");
	}

}
