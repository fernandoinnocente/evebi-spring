package com.evebi.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evebi.application.business.ReprocessValueBusiness;
import com.evebi.application.entities.ItemEntity;

@RestController
public class ItensToReprocessController {

	@Autowired
	private ReprocessValueBusiness business;

	@RequestMapping(value = "/search-reprocess")
	public ResponseEntity<List<ItemEntity>> searchReprocess() {
		List<ItemEntity> reprocessableItems = business.getReprocessableItems();
		
		
		return ResponseEntity.ok(reprocessableItems);
	}

}
