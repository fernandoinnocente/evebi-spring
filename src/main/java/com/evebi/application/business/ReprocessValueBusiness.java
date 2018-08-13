package com.evebi.application.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evebi.application.entities.ItemEntity;
import com.evebi.application.entities.ItemReprocessMaterial;
import com.evebi.application.repository.ItemRepository;

@Service
public class ReprocessValueBusiness {
	
	@Autowired
	private JitaPriceBusiness jitaBusiness;
	
	@Autowired
	private ItemRepository repository;
	
	private double calculateReprocessValue(ItemEntity item) {
		double sum = 0;

		for (ItemReprocessMaterial material : item.getReprocessedMaterials()) {
			sum += material.getMaterial().getJitaBuyPrice();
		}
		return sum;
	}
	
	private List<ItemEntity> filterReprocessableItems(List<ItemEntity> items) {
		return items.stream().filter(i -> i.getReprocessedMaterials().size() > 0).collect(Collectors.toList());
	}
	
	public List<ItemEntity> getReprocessableItems() {
		List<ItemEntity> items = filterReprocessableItems(repository.findByMarketGroupNotNull());
		items = items.subList(100, 101);
		System.out.println("Updating prices:");
		jitaBusiness.updateJitaPrices(items);
		System.out.println("Calculating reprocess value:");
		for(int i = 0; i < items.size(); i++) {
			calculateReprocessValue(items.get(i));
		}
		return items;
	}
	
	
}
