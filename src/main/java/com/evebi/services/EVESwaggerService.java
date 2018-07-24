package com.evebi.services;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.evebi.enumerators.Endpoints;

public class EVESwaggerService {

	private static EVESwaggerService instance;

	public static EVESwaggerService getInstance() {
		if (instance == null) {
			instance = new EVESwaggerService();
		}
		return instance;
	}

	public String get(String route, HashMap<String, String> params) {
		RestTemplate restTemplate = new RestTemplate();
		String url = Endpoints.EVE_SERVER.url() + route;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, params);
		return response.toString();
	}

	public String get(String route) {
		return this.get(route, new HashMap<>());
	}

}
