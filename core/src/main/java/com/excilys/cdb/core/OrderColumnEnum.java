package com.excilys.cdb.core;

/**
 * Enum used in computers queries (column used for ordering)
 * @author Hugo Descamps
 *
 */

public enum OrderColumnEnum {

	COMPUTER, COMPANY, NULL;

	public String toString() {
		switch (this) {
		case COMPUTER:
			return "c.name";
		case COMPANY:
			return "company.name";
		default:
			return "c.id";
		}
	}

}