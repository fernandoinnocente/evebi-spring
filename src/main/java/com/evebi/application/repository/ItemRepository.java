package com.evebi.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evebi.application.entities.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
	public List<ItemEntity> findByMarketGroupNotNull();
	
	public ItemEntity findById(long id);
}
