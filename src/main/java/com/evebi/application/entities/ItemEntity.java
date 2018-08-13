package com.evebi.application.entities;

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

	@Transient
	@JsonIgnore()
	private boolean alreadyUpdated;

	@Transient
	@JsonIgnore()
	private List<ItemEntity> reprocessedEntitys;

	@Transient
	@JsonInclude()
	private double jitaBuyPrice;

	@Transient
	@JsonInclude()
	private double jitaSellPrice;

	@Transient
	@JsonInclude()
	private double reprocessPrice;
	
	@PostLoad
	private void postLoad() {
		reprocessedEntitys = new ArrayList<>();
		for(ItemReprocessMaterial material : reprocessedMaterials) {
			reprocessedEntitys.add(material.getMaterial());
		}
	}

	public double getJitaSellPrice() {
		return jitaSellPrice;
	}

	public void setJitaSellPrice(double jitaSellPrice) {
		this.jitaSellPrice = jitaSellPrice;
	}

	public double getReprocessPrice() {
		return reprocessPrice;
	}

	public void setReprocessPrice(double reprocessPrice) {
		this.reprocessPrice = reprocessPrice;
	}

	public double getJitaBuyPrice() {
		return jitaBuyPrice;
	}

	public void setJitaBuyPrice(double jitaBuyPrice) {
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

}
