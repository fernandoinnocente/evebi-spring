package com.evebi.application.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.evebi.application.keys.ItemCompositeKey;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "invTypeMaterials")
public class ItemReprocessMaterial {

	@EmbeddedId
	@JsonIgnore
	private ItemCompositeKey key;

	@ManyToOne
	@JoinColumn(name = "typeID", insertable = false, updatable = false)
	@JsonBackReference
	private ItemEntity parentModule;
	
	@ManyToOne
	@JoinColumn(name = "materialTypeID", insertable = false, updatable = false)
	@JsonBackReference
	private ItemEntity material;
	
	@Column(name = "quantity")
	private long quantity;

	public ItemEntity getMaterial() {
		return material;
	}

	public void setMaterial(ItemEntity material) {
		this.material = material;
	}

	public ItemEntity getParentModule() {
		return parentModule;
	}

	public void setParentModule(ItemEntity parentModule) {
		this.parentModule = parentModule;
	}

	public ItemCompositeKey getKey() {
		return key;
	}

	public void setKey(ItemCompositeKey key) {
		this.key = key;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
