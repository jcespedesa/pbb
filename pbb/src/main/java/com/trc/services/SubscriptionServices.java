package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.SubscriptionEntity;
import com.trc.repositories.SubscriptionRepository;

@Service
public class SubscriptionServices 
{
	@Autowired
	SubscriptionRepository repository;
	
	public List<SubscriptionEntity> getAllSubscriptions()
    {
        List<SubscriptionEntity> result=(List<SubscriptionEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<SubscriptionEntity>();
        }
    }
	
	public SubscriptionEntity getSubscriptionById(Long id) throws RecordNotFoundException 
    {
        Optional<SubscriptionEntity> subscription=repository.findById(id);
         
        if(subscription.isPresent()) {
            return subscription.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
	public SubscriptionEntity createOrUpdateSubscription(SubscriptionEntity entity) 
    {
        if(entity.getSubscriptionid()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<SubscriptionEntity> subscription=repository.findById(entity.getSubscriptionid());
             
            if(subscription.isPresent()) 
            {
                SubscriptionEntity newEntity=subscription.get();
                
                newEntity.setSubscriptionNum(entity.getSubscriptionNum());
                newEntity.setSubscription(entity.getSubscription());
                                
                newEntity=repository.save(newEntity);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    }
	
	public void deleteSubscriptionById(Long id) throws RecordNotFoundException 
    {
        Optional<SubscriptionEntity> subscription=repository.findById(id);
         
        if(subscription.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No subscription record exist for given id");
        }
    } 

}
