package com.amar.natural.util;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logicoy.pdmpfileexport.util.RequestDTO;

@Component
public class Test {

	public JsonNode getJson(String to, String from, RequestDTO requestDTO) {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate restapi = new RestTemplate();
        JsonNode jsonInfo;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
            headers.add("Content-Type", "application/json");
            String req = "{\"stateCode\":\"" + requestDTO.getStateCode() + "\",\"userRole\":\"" + requestDTO.getUserRole() + "\",\"facilityId\":\"" + requestDTO.getFacilityId() + "\","
                    + "\"organization_name\":\"" + requestDTO.getOrganization_name() + "\",\"providerDEA\":\"" + requestDTO.getProviderDEA() + "\",\"providerNPI\":\"" + requestDTO.getProviderNPI() + "\","
                    + "\"providerStateLicNo\":\"" + requestDTO.getProviderStateLicNo() + "\",\"ehrUserName\":\"" + requestDTO.getEhrUserName() + "\","
                    + "\"top\":\"" + requestDTO.getTop() + "\",\"patientFirstName\":\"" + requestDTO.getPatientFirstName() + "\","
                    + "\"patientLastName\":\"" + requestDTO.getPatientLastName() + "\",\"patientDOB\":\"" + requestDTO.getPatientDOB() + "\","
                    + "\"sexCode\":\"" + requestDTO.getSexCode() + "\",\"providerFirstName\":\"" + requestDTO.getProviderFirstName() + "\","
                    + "\"providerLastName\":\"" + requestDTO.getProviderLastName() + "\",\"partialSearch\":\""
                    + requestDTO.isPartialSearch() + "\",\"status\":\"" + requestDTO.getStatus() + "\",\"isExport\":" + requestDTO.getIsExport() + "}";
            HttpEntity<String> request = new HttpEntity<String>(req, headers);
            String url = "" + "getErrorLogs?fromDate=" + from + "&toDate=" + to;

            if (requestDTO.getStateCode().equalsIgnoreCase("IL")) {
                url = "https://www.ilpmp.org/mips-dashboard/" + "getErrorLogs?fromDate=" + from + "&toDate=" + to;
            }

            ResponseEntity<String> response = restapi.exchange(url, HttpMethod.POST, request, String.class);

            jsonInfo = mapper.readTree(response.getBody());
            if (!response.getBody().equals("No Data Found!")) {
                jsonInfo = mapper.readTree(response.getBody());
                if (jsonInfo != null) {
                    return jsonInfo;
                } else {
                    System.out.println("No data to write in excel, json is null or empty.");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
