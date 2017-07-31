package com.excilys.cdb.persistence;

import com.excilys.cdb.core.User;

public interface UserDao {

	public User getUser(String username);

}
