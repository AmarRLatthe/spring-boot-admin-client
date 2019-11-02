package com.amar.natural.service;

import com.amar.natural.dto.UserDTO;

public interface UserService {
	
	public int createUser(UserDTO userDTO);

	public boolean getUserByEmail(String email);
	
	public UserDTO getUser();
}
