package com.amar.natural.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amar.natural.dao.UserDAOImpl;
import com.amar.natural.dto.UserDTO;

@Component
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserDAOImpl userDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Override
	public int createUser(UserDTO userDTO) {
		logger.info("Inside create user of user service");
		return userDao.createUser(userDTO);
	}

	@Override
	public boolean getUserByEmail(String email) {
		UserDTO list = null;
		boolean validUser = false;
		try {
			list = userDao.getUserByEmail(email);
			if(list.getEmailId().equals(email)) {
				validUser = true;
			}
		} catch (Exception e) {
			logger.info("Error while validating user email id ", e.getMessage());
		}
		return validUser;
	}

	@Override
	public UserDTO getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
