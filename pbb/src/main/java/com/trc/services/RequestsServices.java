package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.RequestsEntity;
import com.trc.repositories.RequestsRepository;


@Service
public class RequestsServices 
{
	@Autowired
	RequestsRepository repository;
	
	     
    public List<RequestsEntity> getAllRequests()
    {
        List<RequestsEntity> result=(List<RequestsEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<RequestsEntity>();
        }
    }
     
    public RequestsEntity getRequestById(Long id) throws RecordNotFoundException 
    {
        Optional<RequestsEntity> request=repository.findById(id);
         
        if(request.isPresent()) {
            return request.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public RequestsEntity createOrUpdateRequest(RequestsEntity entity) 
    {
        if(entity.getRequestid()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<RequestsEntity> request=repository.findById(entity.getRequestid());
             
            if(request.isPresent()) 
            {
            	RequestsEntity newEntity=request.get();
                
                newEntity.setStoreId(entity.getStoreId());
                newEntity.setCname(entity.getCname());
                newEntity.setEmail(entity.getEmail());
                newEntity.setPhone(entity.getPhone());
                newEntity.setProduct(entity.getProduct());
                newEntity.setInstructions(entity.getInstructions());
 
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
     
    public void deleteRequestById(Long id) throws RecordNotFoundException 
    {
        Optional<RequestsEntity> request=repository.findById(id);
         
        if(request.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No request record exist for given id");
        }
    } 
     
   

}
