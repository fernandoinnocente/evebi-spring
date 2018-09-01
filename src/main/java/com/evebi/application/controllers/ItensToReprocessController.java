package com.evebi.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evebi.application.business.ReprocessValueBusiness;
import com.evebi.application.entities.ItemEntity;
import com.evebi.application.json.mappings.ItemPriceJSON;
import com.evebi.application.json.mappings.ItemPriceMapping;

@RestController
public class ItensToReprocessController {

	@Autowired
	private ReprocessValueBusiness business;

	@RequestMapping(value = "/search-reprocess")
	public ResponseEntity<List<ItemEntity>> searchReprocess(@RequestParam("page") int page) {	

		System.out.println("Request recebida: search...");
		long time = System.currentTimeMillis();
		List<ItemEntity> reprocessableItems = business.getReprocessableItems(page);
		System.out.println("Conclusão após " + (System.currentTimeMillis() - time));
		return ResponseEntity.ok(reprocessableItems);
	}

	@RequestMapping(value = "/reprocess/test")
	public ResponseEntity<ItemEntity> test(@RequestParam("product") String productID) {

		System.out.println("Request recebida: test...");
		long time = System.currentTimeMillis();
		ItemEntity item = business.getReprocessableItem(Long.parseLong(productID));
		System.out.println("Conclusão após " + (System.currentTimeMillis() - time));
		return ResponseEntity.ok(item);
	}

	@RequestMapping(value = "/reprocess/instant")
	public ResponseEntity<List<ItemPriceJSON>> instantReprocess(@RequestParam("page") int page) {

		System.out.println("Request recebida: instant...");
		long time = System.currentTimeMillis();
		List<ItemEntity> reprocessableItems = business.getInstantReprocessableItems();
		System.out.println("Conclusão após " + (System.currentTimeMillis() - time));
		return ResponseEntity.ok(new ItemPriceMapping().convert(reprocessableItems));
	}

}
