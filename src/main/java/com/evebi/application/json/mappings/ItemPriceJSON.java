package com.evebi.application.json.mappings;

import java.math.BigDecimal;

public class ItemPriceJSON {

	private String name;

	private BigDecimal jitaBuyPrice;

	private BigDecimal jitaSellPrice;

	private BigDecimal reprocessPrice;

	private long itemsBelowPrice;
	
	public ItemPriceJSON(String name, BigDecimal jitaBuyPrice, BigDecimal jitaSellPrice, BigDecimal reprocessPrice, long itemsBelowPrice) {
		super();
		this.name = name;
		this.jitaBuyPrice = jitaBuyPrice;
		this.jitaSellPrice = jitaSellPrice;
		this.reprocessPrice = reprocessPrice;
		this.itemsBelowPrice = itemsBelowPrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getJitaBuyPrice() {
		return jitaBuyPrice;
	}

	public void setJitaBuyPrice(BigDecimal jitaBuyPrice) {
		this.jitaBuyPrice = jitaBuyPrice;
	}

	public BigDecimal getJitaSellPrice() {
		return jitaSellPrice;
	}

	public void setJitaSellPrice(BigDecimal jitaSellPrice) {
		this.jitaSellPrice = jitaSellPrice;
	}

	public BigDecimal getReprocessPrice() {
		return reprocessPrice;
	}

	public void setReprocessPrice(BigDecimal reprocessPrice) {
		this.reprocessPrice = reprocessPrice;
	}

	public long getItensBelowPrice() {
		return itemsBelowPrice;
	}

	public void setItensBelowPrice(long itensBelowPrice) {
		this.itemsBelowPrice = itensBelowPrice;
	}
}
