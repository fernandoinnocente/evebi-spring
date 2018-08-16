package com.evebi.application.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.evebi.enumerators.Endpoints;

@Service
public class EVESwaggerService {

	@Async
	public CompletableFuture<String> get(String route, Map<String, String> params,
			MultiValueMap<String, String> queryParams) {
		CompletableFuture<String> response = new CompletableFuture<>();

		CompletableFuture.runAsync(() -> {
			RestTemplate restTemplate = new RestTemplate();
			String url = Endpoints.EVE_SERVER.url() + route;
			UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(url)
					.queryParams(queryParams).buildAndExpand(params);

			response.complete(restTemplate.getForObject(uriComponents.toUriString(), String.class));
		});

		return response;
	}

	@Async
	public CompletableFuture<String> get(String route) {
		return this.get(route, new HashMap<>(), new LinkedMultiValueMap<>());
	}

}
