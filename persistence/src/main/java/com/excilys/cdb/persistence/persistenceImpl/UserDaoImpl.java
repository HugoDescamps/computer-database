package com.excilys.cdb.persistence.persistenceImpl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.UserDao;
import com.excilys.cdb.persistence.config.HibernateConfig;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	private UserDaoImpl() {
		
	}

	@Override
	public User getUser(String username) {
		User user = new User();

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			String listCredentialsQuery = "from user where username=:username";
			Query<User> credentialsListQuery = session.createQuery(listCredentialsQuery, User.class);
			credentialsListQuery.setParameter("username", username);
			
			user = credentialsListQuery.getSingleResult();
		}
		return user;
	}

}
