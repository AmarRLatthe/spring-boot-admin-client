package com.amar.natural.service;

import java.util.List;

import com.amar.natural.dto.ResponseDTO;
import com.amar.natural.dto.UserDTO;

public interface UserService {
	
	public ResponseDTO createUser(UserDTO userDTO);

	public List<UserDTO> getUsers();
	
	public UserDTO getUser();
}
