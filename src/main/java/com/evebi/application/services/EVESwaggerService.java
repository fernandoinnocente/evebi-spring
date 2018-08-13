package com.evebi.application.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.evebi.enumerators.Endpoints;

@Service
public class EVESwaggerService {

	public ResponseEntity<String> get(String route, Map<String, String> params, MultiValueMap<String, String> queryParams) {
		RestTemplate restTemplate = new RestTemplate();
		String url = Endpoints.EVE_SERVER.url() + route;
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.scheme("https").host(url)
				.queryParams(queryParams).buildAndExpand(params);
		System.out.println(uriComponents.toUriString());

		ResponseEntity<String> response = restTemplate.getForEntity(uriComponents.toUriString(), String.class);
		return response;
	}

	public ResponseEntity<String> get(String route) {
		return this.get(route, new HashMap<>(), new LinkedMultiValueMap<>());
	}

}
