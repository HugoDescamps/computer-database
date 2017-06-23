package com.excilys.cdb.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.ui.Main;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	static final Logger logger = LoggerFactory.getLogger(Main.class);

	public MyException(String errorMsg) {
		logger.error(errorMsg+"\n");
	}

}
