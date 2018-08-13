package com.evebi.application.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evebi.application.entities.ItemEntity;
import com.evebi.application.models.MarketOrder;
import com.evebi.application.services.MarketOperationsService;

@Service
public class JitaPriceBusiness {

	@Autowired
	MarketOperationsService service;

	private List<MarketOrder> filterJitaOrders(List<MarketOrder> orders) {
		return orders.stream().filter(o -> o.getLocationID() == 60003760).collect(Collectors.toList());
	}

	public ItemEntity updateJitaBuyPrice(ItemEntity item) {
		List<MarketOrder> orders = service.getBuyOrders(String.valueOf(item.getId()), "10000002");
		List<MarketOrder> jitaOrders = filterJitaOrders(orders);
		jitaOrders = service.sortByHighestPrice(jitaOrders);
		if (jitaOrders.size() > 0) {
			item.setJitaBuyPrice(jitaOrders.get(0).getPrice());
		}
		return item;
	}

	public ItemEntity updateJitaSellPrice(ItemEntity item) {
		List<MarketOrder> orders = service.getSellOrders(String.valueOf(item.getId()), "10000002");
		List<MarketOrder> jitaOrders = filterJitaOrders(orders);
		jitaOrders = service.sortByLowestPrice(jitaOrders);
		if (jitaOrders.size() > 0) {
			item.setJitaSellPrice(jitaOrders.get(0).getPrice());
		}
		return item;
	}

	public List<ItemEntity> updateJitaPrices(List<ItemEntity> items) {
		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			if(item.isAlreadyUpdated()) {
				continue;
			}
			updateJitaPrices(item.getReprocessedEntitys());
			updateJitaBuyPrice(item);
			updateJitaSellPrice(item);
			item.setAlreadyUpdated(true);
		}
		return items;
	}

}
