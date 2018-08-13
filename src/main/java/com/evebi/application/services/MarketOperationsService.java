package com.evebi.application.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.evebi.application.models.MarketOrder;
import com.evebi.enumerators.MarketOrderType;
import com.evebi.enumerators.Routes;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MarketOperationsService {

	@Autowired
	private EVESwaggerService service;

	@Autowired
	private ObjectMapper objectMapper;

	public List<MarketOrder> getBuyOrders(String productID, String region) {
		Map<String, String> params = new HashMap<>();
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		params.put("region_id", region);
		queryParams.add("datasource", "tranquility");
		queryParams.add("page", "1");
		queryParams.add("order_type", MarketOrderType.BUY.type());
		queryParams.add("product_id", productID);
		return this.getOrders(params, queryParams);
	}

	public List<MarketOrder> getSellOrders(String productID, String region) {
		Map<String, String> params = new HashMap<>();
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		params.put("region_id", region);
		queryParams.add("datasource", "tranquility");
		queryParams.add("page", "1");
		queryParams.add("order_type", MarketOrderType.SELL.type());
		queryParams.add("product_id", productID);
		return this.getOrders(params, queryParams);
	}
	
	public List<MarketOrder> sortByLowestPrice(List<MarketOrder> orders) {
		
		Collections.sort(orders, new Comparator<MarketOrder>(){
		     public int compare(MarketOrder o1, MarketOrder o2){
		         return Double.compare(o1.getPrice(), o2.getPrice());
		     }
		});
		
		return orders;
	}
	
	public List<MarketOrder> sortByHighestPrice(List<MarketOrder> orders) {
		
		Collections.sort(orders, new Comparator<MarketOrder>(){
		     public int compare(MarketOrder o1, MarketOrder o2){
		         return Double.compare(o2.getPrice(), o1.getPrice());
		     }
		});
		
		return orders;
	}

	private List<MarketOrder> getOrders(Map<String, String> params, MultiValueMap<String, String> queryParams) {
		ResponseEntity<String> response = service.get(Routes.GET_MARKET_ORDERS.route(), params, queryParams);
		List<MarketOrder> orders = null;
		try {
			orders = Arrays.asList(objectMapper.readValue(response.getBody(), MarketOrder[].class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}

}
