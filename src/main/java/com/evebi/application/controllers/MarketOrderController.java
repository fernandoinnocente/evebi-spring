package com.evebi.application.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.evebi.enumerators.Routes;
import com.evebi.services.EVESwaggerService;

@RestController
public class MarketOrderController {

	private EVESwaggerService service = EVESwaggerService.getInstance();

	@RequestMapping("/search-profit/{productID}/{region}")
	public String searchProfit(
			@RequestParam("productID") String productID,
			@RequestParam("region") String region) {
		Map<String,String> params = new HashMap<>();
		params.put("productID", productID);
		params.put("region", region);
		return service.get(Routes.GET_MARKET_ORDERS.route(), );
	}

}
