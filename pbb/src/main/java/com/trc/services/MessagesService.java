package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.MessagesEntity;
import com.trc.repositories.MessagesRepository;

@Service
public class MessagesService 
{
	@Autowired
    MessagesRepository repository;
	
	public List<MessagesEntity> getAllMessages()
    {
        List<MessagesEntity> result=(List<MessagesEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<MessagesEntity>();
        }
    }
	
	public List<MessagesEntity> getAllMessagesDesc()
    {
        List<MessagesEntity> result=(List<MessagesEntity>) repository.findAllDesc();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<MessagesEntity>();
        }
    }
     
    public MessagesEntity getMessageById(Long id) throws RecordNotFoundException 
    {
        Optional<MessagesEntity> message=repository.findById(id);
         
        if(message.isPresent()) 
        {
            return message.get();
        } 
        else 
        {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public MessagesEntity createOrUpdateMessage(MessagesEntity entity) 
    {
        if(entity.getId()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<MessagesEntity> message=repository.findById(entity.getId());
             
            if(message.isPresent()) 
            {
            	MessagesEntity newEntity=message.get();
                
                newEntity.setCname(entity.getCname());
                newEntity.setEmail(entity.getEmail());
                
                newEntity.setPhone(entity.getPhone());
                newEntity.setMessage(entity.getMessage());
                
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
     
    public void deleteMessageById(Long id) throws RecordNotFoundException 
    {
        Optional<MessagesEntity> message=repository.findById(id);
         
        if(message.isPresent()) 
        {
            repository.deleteById(id);
        } 
        else 
        {
            throw new RecordNotFoundException("No Message record exist for given id");
        }
    }
    
    public List<MessagesEntity> listByDateDesc()
    {
    	return repository.listByDateDesc();
    	
    }
    
    public String updateMessage(String outcome,Long id)
    {
    	String message=null;
    	repository.updateOutcome(outcome,id);
    	message="The update operation was successful";
    	
    	return message;
    	
    }

}
