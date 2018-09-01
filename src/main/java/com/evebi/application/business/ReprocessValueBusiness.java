package com.evebi.application.business;

import java.math.BigDecimal;
import java.util.ArrayList;
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

	private final BigDecimal zero = new BigDecimal(0);

	final int MAX_PAGES = 10;

	private BigDecimal calculateReprocessValue(ItemEntity item) {
		BigDecimal sum = new BigDecimal(0);

		for (ItemReprocessMaterial material : item.getReprocessedMaterials()) {
			double quantity = Math.floor(material.getQuantity() / 2);
			BigDecimal jitaPrice = material.getMaterial().getJitaBuyPrice();
			if (jitaPrice != null) {
				sum = sum.add(new BigDecimal(quantity).multiply(jitaPrice));
			}
		}

		BigDecimal batchSize = new BigDecimal(item.getReprocessSize());

		return sum.divide(batchSize);
	}

	private List<ItemEntity> filterReprocessableItems(List<ItemEntity> items) {
		return items.stream().filter(i -> i.getReprocessedMaterials().size() > 0).collect(Collectors.toList());
	}

	private ItemEntity updateReprocessInformations(ItemEntity item, BigDecimal price) {
		item.setReprocessPrice(calculateReprocessValue(item));

		if (price != null && zero.compareTo(price) < 0) {
			item.setReprocessProfitability(item.getReprocessPrice().subtract(price));
		} else {
			item.setReprocessProfitability(zero);
		}

		item.setReprocessProfitable(zero.compareTo(item.getReprocessProfitability()) < 0);
		return item;
	}

	public ItemEntity getReprocessableItem(long id) {
		ItemEntity item = repository.findById(id);
		List<ItemEntity> items = new ArrayList<>();
		items.add(item);
		jitaBusiness.getUpdatedJitaPrices(items);
		updateReprocessInformations(item, item.getJitaBuyPrice());
		return item;
	}

	public List<ItemEntity> getReprocessableItems(int page) {
		List<ItemEntity> items = filterReprocessableItems(repository.findByMarketGroupNotNull());
		int pageSize = items.size() / MAX_PAGES;
		items = items.subList(pageSize * (page - 1), pageSize * page);
		items = jitaBusiness.getUpdatedJitaPrices(items);

		System.out.println("Atualizando informações de reprocessamento");
		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			updateReprocessInformations(item, item.getJitaBuyPrice());
		}

		return items.stream().filter(i -> i.isReprocessProfitable()).collect(Collectors.toList());
	}

	public List<ItemEntity> getInstantReprocessableItems() {
		List<ItemEntity> items = filterReprocessableItems(repository.findByMarketGroupNotNull());
		int pageSize = items.size() / MAX_PAGES;

		System.out.println("Processando " + items.size() + " items.");

		for (int i = 1; i < MAX_PAGES; i++) {
			System.out.println("Processando items " + (pageSize * (i - 1)) + " ao " + (pageSize * i));
			List<ItemEntity> subItems = items.subList(pageSize * (i - 1), pageSize * i);
			jitaBusiness.getUpdatedJitaPrices(subItems);
		}

		System.out.println("Atualizando informações de reprocessamento");
		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			updateReprocessInformations(item, item.getJitaSellPrice());
			long itemsBelowPrice;
			try {
				itemsBelowPrice = item.getJitaSellOrders().stream().filter((o) -> {
					BigDecimal orderPrice = o.getPrice() != null ? o.getPrice() : zero;
					return orderPrice.compareTo(item.getReprocessPrice()) <= 0;
				}).mapToLong(o -> o.getRemainVolume()).sum();
			} catch (Exception e) {
				itemsBelowPrice = 0;
			}
			item.setItensBelowReprocessPrice(itemsBelowPrice);
		}

		return items.stream().filter(i -> i.isReprocessProfitable()).collect(Collectors.toList());
	}

}
