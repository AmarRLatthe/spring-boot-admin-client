package com.amar.natural.util;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryObjectUtil {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private static SessionFactory sessionFactory;
	
	public SessionFactoryObjectUtil() {
	}
	
	public SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		}
		
		return sessionFactory;
	}
}
