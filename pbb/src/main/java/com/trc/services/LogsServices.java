package com.trc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.LogsEntity;
import com.trc.repositories.LogsRepository;

@Service
public class LogsServices 
{

	@Autowired
	LogsRepository repository;
	
	public List<LogsEntity> getAllLogs()
    {
        List<LogsEntity> result=(List<LogsEntity>) repository.findAll();
         
        return result;
        
    }
	
	public void createLog(String subject,String description,String clientId)
    {
        repository.generateLog(subject,description,clientId);
         
               
    }
	
}
