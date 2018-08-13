package com.evebi.enumerators;

public enum Endpoints {
	
	EVE_SERVER("esi.evetech.net");

    private String url;

	Endpoints(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

}
