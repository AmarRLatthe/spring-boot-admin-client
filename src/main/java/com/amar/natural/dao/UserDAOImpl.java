package com.amar.natural.dao;


import javax.persistence.EntityManagerFactory;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amar.natural.dto.UserDTO;
import com.amar.natural.util.SessionFactoryObjectUtil;

@Component
public class UserDAOImpl implements UserDAO {

	private static final String GET_USERS = "from ";
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
//	@Autowired
//	private SessionFactoryObjectUtil factoryObjectUtil;
	
	Session session = null;
	@Override
	public UserDTO getUserByEmail(String email) {
		String hql = "from UserDTO u where u.emailId=: email";
		///session = factoryObjectUtil.getSessionFactory().openSession();
		session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		UserDTO userDto = (UserDTO) query.list();
		return userDto;
	}

	@Override
	public int createUser(UserDTO userDTO) {
		
		Transaction transaction = null;
		Integer id = 0;
		session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
		//session = factoryObjectUtil.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			id = (Integer) session.save(userDTO);
			session.flush();
			session.clear();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
		}
		return id;
	}

	@Override
	public void updateUser() {
		
	}

}
