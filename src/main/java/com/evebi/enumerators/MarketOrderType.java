package com.evebi.enumerators;

public enum MarketOrderType {

	BUY("buy"), SELL("sell"), ALL("all");

	private String type;

	MarketOrderType(String type) {
		this.type = type;
	}

	public String type() {
		return type;
	}

}
