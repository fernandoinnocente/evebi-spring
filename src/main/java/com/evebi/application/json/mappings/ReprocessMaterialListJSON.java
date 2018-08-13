package com.evebi.application.json.mappings;

public class ReprocessMaterialListJSON {
	private String name;
	private long quantity;
	
	public ReprocessMaterialListJSON(String name, long quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
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
	
}
