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

	private BigDecimal calculateReprocessValue(ItemEntity item) {
		BigDecimal sum = new BigDecimal(0);

		for (ItemReprocessMaterial material : item.getReprocessedMaterials()) {
			sum = sum.add(material.getMaterial().getJitaBuyPrice().multiply(new BigDecimal(material.getQuantity())));
		}
		return sum.divide((new BigDecimal(item.getReprocessSize()).multiply(new BigDecimal(2))));
	}

	private List<ItemEntity> filterReprocessableItems(List<ItemEntity> items) {
		return items.stream().filter(i -> i.getReprocessedMaterials().size() > 0).collect(Collectors.toList());
	}

	private ItemEntity updateReprocessInformations(ItemEntity item, BigDecimal price ) {
		System.out.println("Atualizando informações de lucratividade do item " + item.getId());
		item.setReprocessPrice(calculateReprocessValue(item));

		if (price.compareTo(new BigDecimal(0)) > 0) {
			item.setReprocessProfitability(item.getReprocessPrice().subtract(price));
		} else {
			item.setReprocessProfitability(new BigDecimal(0));
		}

		item.setReprocessProfitable(item.getReprocessProfitability().compareTo(new BigDecimal(0)) > 0);
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

	public List<ItemEntity> getReprocessableItems(int start, int last) {
		List<ItemEntity> items = filterReprocessableItems(repository.findByMarketGroupNotNull());
		items = items.subList(start, last);
		items = jitaBusiness.getUpdatedJitaPrices(items);

		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			updateReprocessInformations(item, item.getJitaBuyPrice());
		}

		return items.stream().filter(i -> i.isReprocessProfitable()).collect(Collectors.toList());
	}

	public List<ItemEntity> getInstantReprocessableItems(int start, int last) {
		List<ItemEntity> items = filterReprocessableItems(repository.findByMarketGroupNotNull());
		items = items.subList(start, last);
		items = jitaBusiness.getUpdatedJitaPrices(items);
		for (int i = 0; i < items.size(); i++) {
			ItemEntity item = items.get(i);
			updateReprocessInformations(item, item.getJitaSellPrice());
		}

		return items.stream().filter(i -> i.isReprocessProfitable()).collect(Collectors.toList());
	}

}
