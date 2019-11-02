package com.amar.natural.dao;

import com.amar.natural.dto.UserDTO;

public interface UserDAO {

	public UserDTO getUserByEmail(String email);
	
	public int createUser(UserDTO userDTO);
	
	public void updateUser();
}
