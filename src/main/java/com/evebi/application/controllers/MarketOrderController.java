package com.evebi.application.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evebi.application.models.MarketOrder;
import com.evebi.application.services.MarketOperationsService;

@RestController
public class MarketOrderController {
	
	@Autowired
	private MarketOperationsService service;

	@RequestMapping(value ="/search-profit")
	public ResponseEntity<List<MarketOrder>> searchProfit(
			@RequestParam("product") String productID,
			@RequestParam("region") String region) throws InterruptedException, ExecutionException {
		Map<String,String> params = new HashMap<>();
		params.put("product_id", productID);
		params.put("region_id", region);
		return ResponseEntity.ok(service.getBuyOrders(productID, region).get());
	}
	
	@RequestMapping(value ="/market/test")
	public ResponseEntity<List<MarketOrder>> test(
			@RequestParam("product") String productID,
			@RequestParam("region") String region) throws InterruptedException, ExecutionException {
		Map<String,String> params = new HashMap<>();
		params.put("product_id", productID);
		params.put("region_id", region);
		List<MarketOrder> orders = service.getBuyOrders(productID, region).get();
		service.sortByHighestPrice(orders);
		return ResponseEntity.ok(orders);
	}

}
