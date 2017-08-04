package com.excilys.cdb.webapp.validator;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

/**
 * Validates the 2 constraints on our models : names are mandatory, & if both dates are present, discontinued date must be after introduced date
 * @author Hugo Descamps
 *
 */
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
