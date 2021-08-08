package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.SubscriptionEntity;


@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity,Long>  
{
	SubscriptionEntity findBySubscription(String subscription);
}
