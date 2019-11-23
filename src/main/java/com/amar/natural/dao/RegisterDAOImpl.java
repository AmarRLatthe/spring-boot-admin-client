package com.amar.natural.dao;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amar.natural.dto.RegisterDTO;
import com.amar.natural.util.SessionFactoryObjectUtil;

@Repository
public class RegisterDAOImpl implements RegisterDAO {

	private Logger logger = LoggerFactory.getLogger(RegisterDAOImpl.class);
	
	@Autowired
	private SessionFactoryObjectUtil factory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	public int registerUser(RegisterDTO registerDTO) {
		logger.info("Inside registerUser of RegisterDAOImpl");
		Session session = null;
		Transaction transaction = null;
		Integer id = 0;
		try {
			session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
			//session = factory.getSessionFactory().openSession();
			id = (Integer) session.save(registerDTO);
			transaction = session.beginTransaction();
			logger.info("User registered with id ::: " +id); 
		} catch (Exception e) {
			logger.info("Exception while registering user inside registerUser of RegisterDAOImpl");
		}
		return id;
	}

}
