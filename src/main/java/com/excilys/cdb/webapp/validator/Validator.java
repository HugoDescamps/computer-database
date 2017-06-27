package com.excilys.cdb.webapp.validator;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

public class Validator {
	
	public static boolean datesValidator(LocalDate introducedDate, LocalDate discontinuedDate) {

		boolean res = false;

		if (introducedDate == null || discontinuedDate == null) {
			res = true;
		} else {
			if (discontinuedDate.isAfter(introducedDate)) {
				res = true;
			}
		}
		return res;
	}
	
	public static boolean nameValidator(String name) {
		
		boolean res = false;
		
		if (StringUtils.isNotBlank(name)) {
			res = true;
		}
		
		return res;
	}

}
