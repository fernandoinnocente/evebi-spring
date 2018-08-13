package com.evebi.application.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ItemCompositeKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "typeID")
	private long module;

	@Column(name = "materialTypeID")
	private long material;

	public long getModule() {
		return module;
	}

	public void setModule(long module) {
		this.module = module;
	}

	public long getMaterial() {
		return material;
	}

	public void setMaterial(long material) {
		this.material = material;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (material ^ (material >>> 32));
		result = prime * result + (int) (module ^ (module >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCompositeKey other = (ItemCompositeKey) obj;
		if (material != other.material)
			return false;
		if (module != other.module)
			return false;
		return true;
	}

}
