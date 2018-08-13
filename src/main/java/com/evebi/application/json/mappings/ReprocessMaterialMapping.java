package com.evebi.application.json.mappings;

import java.util.ArrayList;
import java.util.List;

import com.evebi.application.entities.ItemReprocessMaterial;
import com.fasterxml.jackson.databind.util.StdConverter;

public class ReprocessMaterialMapping
		extends StdConverter<List<ItemReprocessMaterial>, List<ReprocessMaterialListJSON>> {

	@Override
	public List<ReprocessMaterialListJSON> convert(List<ItemReprocessMaterial> items) {
		List<ReprocessMaterialListJSON> list = new ArrayList<>();
		for (ItemReprocessMaterial item : items) {
			ReprocessMaterialListJSON json = new ReprocessMaterialListJSON(
					item.getMaterial().getName(),
					item.getQuantity());
			list.add(json);
		}
		return list;
	}

}
