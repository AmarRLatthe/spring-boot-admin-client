package com.amar.natural.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amar.natural.dto.RegisterDTO;
import com.amar.natural.dto.ResponseDTO;
import com.amar.natural.dto.UserDTO;
import com.amar.natural.service.RegisterServiceImpl;
import com.amar.natural.service.UserServiceImpl;

@RestController
@CrossOrigin()
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceImpl serviceImpl;

	@Autowired
	private RegisterServiceImpl registerServiceImpl;

	public UserController() {
		logger.info("Created : ", this.getClass().getSimpleName());
	}

	@PostMapping("/createUser")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userDTO) {
		logger.info("Inside createUser " + userDTO);
		ResponseEntity<ResponseDTO> response = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			int validUser = serviceImpl.createUser(userDTO);
			if (validUser != 0) {
				logger.info("Inside if ::::::: ");
				responseDTO.setDescription("User created successfully");
				responseDTO.setResponse("Success");
				responseDTO.setId(validUser);
				response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
				logger.info("Inside if ::::::: ");
			} else {
				responseDTO.setDescription("User creation was unsuccessfully");
				responseDTO.setResponse("Failure");
				response = new ResponseEntity<>(responseDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setDescription(e.getLocalizedMessage());
			responseDTO.setResponse("Failure");
			response = new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
		return response;
	}

	@GetMapping("/validateEmail")
	@ResponseStatus
	public ResponseEntity<ResponseDTO> validateEmail(@RequestBody String email) {
		logger.info("Inside validateEmail :::: ");

		return null;
	}

	@PostMapping("/register")
	@ResponseStatus
	public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterDTO registerDTO) {
		logger.info("Inside registerUser :::: ");
		ResponseEntity<ResponseDTO> response = null;
		ResponseDTO dto = new ResponseDTO();
		try {
			int userCreated = registerServiceImpl.registerUser(registerDTO);
			if (userCreated > 0) {
				dto.setDescription("User registered successfully");
				dto.setResponse("Success");
				dto.setId(userCreated);
				response = new ResponseEntity<>(dto, HttpStatus.CREATED);
			} else {
				dto.setDescription("User registration was unsuccessfully");
				dto.setResponse("Failure");
				response = new ResponseEntity<>(dto, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dto.setDescription(e.getLocalizedMessage());
			dto.setResponse("Failure");
			response = new ResponseEntity<>(dto, HttpStatus.OK);
		}
		return response;
	}
}
