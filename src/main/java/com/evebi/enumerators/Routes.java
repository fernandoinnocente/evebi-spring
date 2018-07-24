package com.evebi.enumerators;

public enum Routes {

	GET_MARKET_ORDERS("/v1/markets/{regionID}/orders/{type_id}");

	private String route;

	Routes(String route) {
		this.route = route;
	}

	public String route() {
		return route;
	}

}
