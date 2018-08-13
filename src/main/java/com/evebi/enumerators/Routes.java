package com.evebi.enumerators;

public enum Routes {

	GET_MARKET_ORDERS("/v1/markets/{region_id}/orders/");

	private String route;

	Routes(String route) {
		this.route = route;
	}

	public String route() {
		return route;
	}

}
