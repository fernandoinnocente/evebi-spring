package com.evebi.application.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarketOrder {

	private int duration;
	
	@JsonProperty("is_buy_order")  
	private boolean isBuyOrder;
	
	private Date issued;
	
	@JsonProperty("location_id")
	private long locationID;
	
	@JsonProperty("min_volume")
	private int minVolume;
	
	@JsonProperty("order_id")
	private long orderID;
	
	private double price;
	
	private String range;
	
	@JsonProperty("system_id")
	private long systemID;
	
	@JsonProperty("type_id")
	private long typeID;
	
	@JsonProperty("volume_remain")
	private long remainVolume;
	
	@JsonProperty("volume_total")
	private long totalVolume;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isBuyOrder() {
		return isBuyOrder;
	}

	public void setBuyOrder(boolean isBuyOrder) {
		this.isBuyOrder = isBuyOrder;
	}

	public Date getIssued() {
		return issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	public long getLocationID() {
		return locationID;
	}

	public void setLocationID(long locationID) {
		this.locationID = locationID;
	}

	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public long getSystemID() {
		return systemID;
	}

	public void setSystemID(long systemID) {
		this.systemID = systemID;
	}

	public long getTypeID() {
		return typeID;
	}

	public void setTypeID(long typeID) {
		this.typeID = typeID;
	}

	public long getRemainVolume() {
		return remainVolume;
	}

	public void setRemainVolume(long remainVolume) {
		this.remainVolume = remainVolume;
	}

	public long getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}

}
