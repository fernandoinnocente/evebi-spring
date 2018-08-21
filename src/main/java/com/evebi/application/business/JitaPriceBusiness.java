package com.evebi.application.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.evebi.application.entities.ItemEntity;
import com.evebi.application.models.MarketOrder;
import com.evebi.application.services.MarketOperationsService;

@Service
public class JitaPriceBusiness {

	@Autowired
	MarketOperationsService service;

	private List<CompletableFuture<ItemEntity>> futuresList = new ArrayList<>();

	private List<MarketOrder> filterJitaOrders(List<MarketOrder> orders) {
		return orders.stream().filter(o -> o.getLocationID() == 60003760).collect(Collectors.toList());
	}

	@Async
	public void updateJitaBuyPrice(ItemEntity item) {
		try {
			List<MarketOrder> orders = service.getBuyOrders(String.valueOf(item.getId()), "10000002").get();
			List<MarketOrder> jitaOrders = service.sortByHighestPrice(filterJitaOrders(orders));
			item.setJitaBuyOrders(jitaOrders);
			BigDecimal price = new BigDecimal(0);
			if (jitaOrders.size() > 0) {
				price = jitaOrders.get(0).getPrice();
			}
			item.setJitaBuyPrice(price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Async
	public void updateJitaSellPrice(ItemEntity item) {
		try {
			Future<List<MarketOrder>> orders = service.getSellOrders(String.valueOf(item.getId()), "10000002");
			List<MarketOrder> jitaOrders = filterJitaOrders(orders.get());
			jitaOrders = service.sortByLowestPrice(jitaOrders);
			item.setJitaSellOrders(jitaOrders);
			BigDecimal price = new BigDecimal(0);
			if (jitaOrders.size() > 0) {
				price = jitaOrders.get(0).getPrice();
			}
			item.setJitaSellPrice(price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Async
	private void fetchUpdatedPrices(ItemEntity item) {

		CompletableFuture<ItemEntity> priceFuture = new CompletableFuture<>();

		CompletableFuture<Void> buyPrice = CompletableFuture.runAsync(() -> updateJitaBuyPrice(item));
		CompletableFuture<Void> sellPrice = CompletableFuture.runAsync(() -> updateJitaSellPrice(item));

		CompletableFuture<Void> combinedUpdates = CompletableFuture.allOf(buyPrice, sellPrice);

		combinedUpdates.thenApply(v -> {
			priceFuture.complete(item);
			return v;
		});

		futuresList.add(priceFuture);
	}

	@Async
	private void fetchUpdatedPrices(List<ItemEntity> items) {

		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			if (item.isAlreadyUpdated()) {
				continue;
			}

			item.setAlreadyUpdated(true);
			fetchUpdatedPrices(item);
			fetchUpdatedPrices(item.getReprocessedEntitys());
		}

	}

	public List<ItemEntity> getUpdatedJitaPrices(List<ItemEntity> items) {
		futuresList = new ArrayList<>();
		fetchUpdatedPrices(items);
		CompletableFuture<ItemEntity>[] futureArray = new CompletableFuture[futuresList.size()];
		futureArray = futuresList.toArray(futureArray);
		CompletableFuture.allOf(futureArray).join();
		return items;
	}

}
