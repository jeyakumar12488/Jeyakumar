package com.BillenniumInterviewTask.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.BillenniumInterviewTask.Entity.RestAPIUser;

public class RestAPI {

	
	private static final String GET_ALL_USERS_API ="http://localhost:8080/api/users";
	private static final String GET_USER_BY_ID_API ="http://localhost:8080/api/users/{id}";
	private static final String CREATE_USER_API ="http://localhost:8080/api/users";
	private static final String UPDATE_USER_API ="http://localhost:8080/api/users/{id}";
	private static final String DELETE_USER_API ="http://localhost:8080/api/users/{id}";


	static RestTemplate restTemplate = new RestTemplate();
	public static void main(String[] args)
	{
		callGetAllUsersAPI();
		callGetUserByIdAPI();
		callDeleteUserByIdAPI();
	}
	
	private static void callGetAllUsersAPI() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<String> result = 
				//restTemplate.exchange(GET_ALL_USERS_API, HttpMethod.GET, entity, String.class);	
		restTemplate.exchange(GET_ALL_USERS_API, HttpMethod.GET, entity, String.class);
		System.out.println(result);
	} 
	
	private static void callGetUserByIdAPI()
	{
		Map<String, Integer> param = new HashMap<>();
		param.put("id", 2);
		
		RestAPIUser user = restTemplate.getForObject(GET_USER_BY_ID_API, RestAPIUser.class, param);
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getEmail());
	} 

	static void callDeleteUserByIdAPI()
	{
		Map<String, Integer> param = new HashMap<>();
		param.put("id", 7);
		restTemplate.delete(DELETE_USER_API, param);
		}
}