package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.RequestsEntity;

@Repository
public interface RequestsRepository  extends CrudRepository<RequestsEntity,Long> 
{
	RequestsEntity findByStoreId(String storeId);
}
