package com.evebi.application.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.evebi.application.json.mappings.ReprocessMaterialMapping;
import com.evebi.application.models.MarketOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "invTypes")
public class ItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typeID")
	private long id;

	@Column(name = "typeName")
	private String name;

	@OneToMany(mappedBy = "parentModule")
	@JsonSerialize(converter = ReprocessMaterialMapping.class)
	private List<ItemReprocessMaterial> reprocessedMaterials;

	@Column(name = "marketGroupID")
	private String marketGroup;
	
	@Column(name = "portionSize")
	private long reprocessSize;

	@Transient
	@JsonIgnore()
	private boolean alreadyUpdated;

	@Transient
	@JsonIgnore()
	private List<ItemEntity> reprocessedEntitys;

	@Transient
	@JsonInclude()
	private BigDecimal jitaBuyPrice;

	@Transient
	@JsonInclude()
	private BigDecimal jitaSellPrice;

	@Transient
	@JsonInclude()
	private BigDecimal reprocessPrice;

	@Transient
	@JsonInclude()
	private boolean isReprocessProfitable;

	@Transient
	@JsonInclude()
	private BigDecimal reprocessProfitability;
	
	@Transient
	@JsonInclude()
	private long itensBelowReprocessPrice;
	
	@Transient
	@JsonIgnore()
	private List<MarketOrder> jitaBuyOrders;
	
	@Transient
	@JsonIgnore()
	private List<MarketOrder> jitaSellOrders;
	


	@PostLoad
	private void postLoad() {
		reprocessedEntitys = new ArrayList<>();
		for (ItemReprocessMaterial material : reprocessedMaterials) {
			reprocessedEntitys.add(material.getMaterial());
		}
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

	public BigDecimal getJitaBuyPrice() {
		return jitaBuyPrice;
	}

	public void setJitaBuyPrice(BigDecimal jitaBuyPrice) {
		this.jitaBuyPrice = jitaBuyPrice;
	}

	public String getMarketGroup() {
		return marketGroup;
	}

	public void setMarketGroup(String marketGroup) {
		this.marketGroup = marketGroup;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemReprocessMaterial> getReprocessedMaterials() {
		return reprocessedMaterials;
	}

	public void setReprocessedMaterials(List<ItemReprocessMaterial> reprocessedMaterials) {
		this.reprocessedMaterials = reprocessedMaterials;
	}

	public boolean isAlreadyUpdated() {
		return alreadyUpdated;
	}

	public void setAlreadyUpdated(boolean alreadyUpdated) {
		this.alreadyUpdated = alreadyUpdated;
	}

	public List<ItemEntity> getReprocessedEntitys() {
		return reprocessedEntitys;
	}

	public void setReprocessedEntitys(List<ItemEntity> reprocessedEntitys) {
		this.reprocessedEntitys = reprocessedEntitys;
	}

	public boolean isReprocessProfitable() {
		return isReprocessProfitable;
	}

	public void setReprocessProfitable(boolean isReprocessProfitable) {
		this.isReprocessProfitable = isReprocessProfitable;
	}

	public BigDecimal getReprocessProfitability() {
		return reprocessProfitability;
	}

	public void setReprocessProfitability(BigDecimal reprocessProfitability) {
		this.reprocessProfitability = reprocessProfitability;
	}

	public long getReprocessSize() {
		return reprocessSize;
	}

	public void setReprocessSize(long reprocessSize) {
		this.reprocessSize = reprocessSize;
	}

	public List<MarketOrder> getJitaBuyOrders() {
		return jitaBuyOrders;
	}

	public void setJitaBuyOrders(List<MarketOrder> jitaBuyOrders) {
		this.jitaBuyOrders = jitaBuyOrders;
	}

	public List<MarketOrder> getJitaSellOrders() {
		return jitaSellOrders;
	}

	public void setJitaSellOrders(List<MarketOrder> jitaSellOrders) {
		this.jitaSellOrders = jitaSellOrders;
	}

	public long getItensBelowReprocessPrice() {
		return itensBelowReprocessPrice;
	}

	public void setItensBelowReprocessPrice(long itensBelowReprocessPrice) {
		this.itensBelowReprocessPrice = itensBelowReprocessPrice;
	}

}
