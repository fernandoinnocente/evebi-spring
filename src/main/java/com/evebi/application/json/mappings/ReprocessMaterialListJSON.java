package com.evebi.application.json.mappings;

import java.math.BigDecimal;

public class ReprocessMaterialListJSON {
	private String name;
	private long quantity;
	private BigDecimal price;

	public ReprocessMaterialListJSON(String name, long quantity, BigDecimal price) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
