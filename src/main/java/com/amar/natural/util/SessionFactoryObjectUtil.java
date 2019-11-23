package com.amar.natural.util;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryObjectUtil {

//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
//	
//	@Autowired
//	private LocalSessionFactoryBean factoryBean;
//	
//	private static SessionFactory sessionFactory;
//	
//	public SessionFactoryObjectUtil() {
//	}
//	
//	public SessionFactory getSessionFactory() {
//		if(sessionFactory == null) {
//			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
//		}
//		
//		return sessionFactory;
//	}
}
