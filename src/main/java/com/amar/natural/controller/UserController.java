package com.amar.natural.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amar.natural.dto.RegisterDTO;
import com.amar.natural.dto.ResponseDTO;
import com.amar.natural.dto.UserDTO;
import com.amar.natural.service.RegisterServiceImpl;
import com.amar.natural.service.UserServiceImpl;
import com.amar.natural.util.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicoy.pdmpfileexport.util.GenericExcelBuilder;
import com.logicoy.pdmpfileexport.util.RequestDTO;

import io.swagger.annotations.ApiModelProperty;

@RestController
@CrossOrigin()
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserServiceImpl serviceImpl;

	@Autowired
	private RegisterServiceImpl registerServiceImpl;
	
	@Autowired
	private Environment environment;
	
	private GenericExcelBuilder excel;
	
	@Autowired
	private Test test;

	public UserController() {
		logger.info("Created : ", this.getClass().getSimpleName());
	}

	@ApiModelProperty(value = "the id of the item", required = true)
	@PostMapping("/createUser")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userDTO) {
		logger.info("Inside createUser " + userDTO);
		ResponseEntity<ResponseDTO> response = null;
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			int validUser = serviceImpl.createUser(userDTO);
			if (validUser != 0) {
				responseDTO.setDescription("User created successfully");
				responseDTO.setResponse("Success");
				responseDTO.setId(validUser);
				response = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
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
	
	@PostMapping(value = "/generate-report")
	public ResponseEntity<String> getTxnLogs(@RequestBody RequestDTO requestDTO,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "fileType", required = false) String fileType,
			@RequestParam(value = "txnType", required = false) String txnType) throws IOException {
		String emailSubject = environment.getProperty("email.error.file.subject");
		String emailBody = environment.getProperty("email.error.file.body");
		String emailTo = environment.getProperty("email.error.file.to");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonInfo = test.getJson("", "2019-11-19", requestDTO);
		excel = new GenericExcelBuilder();
		String str = excel.writeErrorReport(jsonInfo, "IL");
		System.out.println("Data  " +str);
		
		FileOutputStream fos = new FileOutputStream("D:\\D\\logicoy\\test1.xls"); 
		byte[] bytes = Base64.getDecoder().decode(str);
        fos.write(bytes);
        
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
		String fileName = "IL_ERROR_REPORT" + format.format(new Date()) + ".xls";
		emailBody = emailBody.replace("@env", environment.getProperty("application.env"));
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setBufferRequestBody(false);
		RestTemplate restapi = new RestTemplate(factory);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "29HOQ3QTMCF2KS2foCEmNmgowhhUHh2h");
			String req = "{\"reciverMailId\":\"" + emailTo + "\",\"subject\":\"" + emailSubject
					+ "\",\"message\":\"" + emailBody + "\",\"fileName\":\"" + fileName + "\",\"file\":\"" + str
					+ "\"}";
			HttpEntity<String> request = new HttpEntity<String>(req, headers);
			String url = "https://openid.logicoy.com/logicoy/sendSecureEmailWithAttachment";
			ResponseEntity<String> response = restapi.exchange(url, HttpMethod.POST, request, String.class);
			logger.info("Response :::: " + response.getStatusCode().toString().contains("200"));
			if (response.getStatusCode().toString().contains("200")) {
				jsonInfo = mapper.readTree(response.getBody());
				logger.info("mapper.readTree(response.getBody()) " + mapper.readTree(response.getBody()));
				if (jsonInfo.get("message").asText().contains("email has been sent successfully")) {
					logger.info("Response :::: " + response);
				} else {
					logger.info("Something went wrong while sending error report email");
				}
			} else {
				logger.info("Something went wrong while calling error email notification api");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = Base64.getDecoder().decode(str);
		try (OutputStream stream = new FileOutputStream("D:\\D\\logicoy\\test.xls")) {
		    stream.write(data);
		}
		return null;	
	}
}
