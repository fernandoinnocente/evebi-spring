package com.evebi.application.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.evebi.application.models.MarketOrder;
import com.evebi.enumerators.MarketOrderType;
import com.evebi.enumerators.Routes;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MarketOperationsService {

	@Autowired
	private EVESwaggerService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Async
	public CompletableFuture<List<MarketOrder>> getBuyOrders(String productID, String region) {
		Map<String, String> params = new HashMap<>();
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		params.put("region_id", region);
		queryParams.add("datasource", "tranquility");
		queryParams.add("page", "1");
		queryParams.add("order_type", MarketOrderType.BUY.type());
		queryParams.add("type_id", productID);
		return CompletableFuture.completedFuture(this.getOrders(params, queryParams));
	}

	@Async
	public CompletableFuture<List<MarketOrder>> getSellOrders(String productID, String region) {
		Map<String, String> params = new HashMap<>();
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		params.put("region_id", region);
		queryParams.add("datasource", "tranquility");
		queryParams.add("page", "1");
		queryParams.add("order_type", MarketOrderType.SELL.type());
		queryParams.add("type_id", productID);
		return CompletableFuture.completedFuture(this.getOrders(params, queryParams));
	}

	public List<MarketOrder> sortByLowestPrice(List<MarketOrder> orders) {

		Collections.sort(orders, new Comparator<MarketOrder>() {
			public int compare(MarketOrder o1, MarketOrder o2) {
				return o1.getPrice().compareTo(o2.getPrice());
			}
		});

		return orders;
	}

	public List<MarketOrder> sortByHighestPrice(List<MarketOrder> orders) {

		Collections.sort(orders, new Comparator<MarketOrder>() {
			public int compare(MarketOrder o1, MarketOrder o2) {
				return o2.getPrice().compareTo(o1.getPrice());
			}
		});

		return orders;
	}

	private List<MarketOrder> getOrders(Map<String, String> params, MultiValueMap<String, String> queryParams) {
		List<MarketOrder> orders = null;
		System.out.println("Requisitando preços do tipo " + queryParams.get("order_type") + " para item " + queryParams.get("type_id"));
		try {
			String response = service.get(Routes.GET_MARKET_ORDERS.route(), params, queryParams).get();
			orders = Arrays.asList(objectMapper.readValue(response, MarketOrder[].class));
			System.out.println("Ordens Resolvidas:");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}

}
