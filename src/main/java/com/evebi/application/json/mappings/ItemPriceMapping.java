package com.evebi.application.json.mappings;

import java.util.ArrayList;
import java.util.List;

import com.evebi.application.entities.ItemEntity;
import com.fasterxml.jackson.databind.util.StdConverter;

public class ItemPriceMapping extends StdConverter<List<ItemEntity>, List<ItemPriceJSON>> {

	@Override
	public List<ItemPriceJSON> convert(List<ItemEntity> items) {
		List<ItemPriceJSON> list = new ArrayList<>();
		for (ItemEntity item : items) {
			ItemPriceJSON json = new ItemPriceJSON(
					item.getName(),
					item.getJitaBuyPrice(),
					item.getJitaSellPrice(),
					item.getReprocessPrice(),
					item.getItensBelowReprocessPrice());
			list.add(json);
		}
		return list;
	}

}
