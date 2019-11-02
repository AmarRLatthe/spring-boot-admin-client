package com.amar.natural.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amar.natural.dao.RegisterDAOImpl;
import com.amar.natural.dto.RegisterDTO;

@Service
public class RegisterServiceImpl implements RegisterService {

	private Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	private RegisterDAOImpl registerDaoImpl;
	
	@Override
	public int registerUser(RegisterDTO registerDTO) {
		logger.info("Inside registerUser of RegisterDAOImpl");
		return registerDaoImpl.registerUser(registerDTO);
	}

}
