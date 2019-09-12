package com.amar.natural.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amar.natural.dto.ResponseDTO;
import com.amar.natural.dto.UserDTO;

@RestController
@RequestMapping("/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	public UserController() {
		logger.info("Created : ", this.getClass().getSimpleName());
	}
	
	@PostMapping("/createUser")
	@ResponseStatus()
	public ResponseEntity<ResponseDTO> createUser(UserDTO userDTO) {
		ResponseEntity<ResponseDTO> response = null;
		ResponseDTO responseDTO = null;
		try {
			//responseDTO = userService.createUser(userDTO);
			if(responseDTO.getResponse().equals("Success")) {
				response = new ResponseEntity<ResponseDTO>(HttpStatus.CREATED);
			} else {
				
			}
		} catch (Exception e) {
			
		}
		return response;
	}
}
