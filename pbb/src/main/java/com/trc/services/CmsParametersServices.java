package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.CmsParametersEntity;
import com.trc.repositories.CmsParametersRepository;
import com.trc.repositories.LogsRepository;


@Service
public class CmsParametersServices 
{
	@Autowired
    CmsParametersRepository repository;
	
	@Autowired
    LogsRepository repositoryLogs;
     
    public List<CmsParametersEntity> getAllCmsParameters()
    {
        List<CmsParametersEntity> result=(List<CmsParametersEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<CmsParametersEntity>();
        }
    }
     
    public CmsParametersEntity getCmsParameterById(Long id) throws RecordNotFoundException 
    {
        Optional<CmsParametersEntity> cmsParameter=repository.findById(id);
         
        if(cmsParameter.isPresent()) 
        {
            return cmsParameter.get();
        } 
        else 
        {
            throw new RecordNotFoundException("No record exist for given id..");
        }
    }
     
    public CmsParametersEntity createOrUpdateCmsParameter(CmsParametersEntity entity) 
    {
        if(entity.getParamid()==null) 
        {
            entity=repository.save(entity);
            
          //Converting long to string
        	String clientIdString=String.valueOf(entity.getPriznak());
        	
        	//Creating the log
        	repositoryLogs.generateLog("New CMS parameter creation","New parameter is being created",clientIdString);
             
            
             
            return entity;
        } 
        else
        {
            Optional<CmsParametersEntity> cmsParameter=repository.findById(entity.getParamid());
             
            if(cmsParameter.isPresent()) 
            {
            	CmsParametersEntity newEntity=cmsParameter.get();
                
                newEntity.setPriznak(entity.getPriznak());
                
                newEntity.setParam1(entity.getParam1());
                newEntity.setParam2(entity.getParam2());
                newEntity.setParam3(entity.getParam3());
                newEntity.setParam4(entity.getParam4());
                
                newEntity.setSentence1(entity.getSentence1());
                newEntity.setSentence2(entity.getSentence2());
                newEntity.setSentence3(entity.getSentence3());
                newEntity.setSentence4(entity.getSentence4());
                
                newEntity.setText1(entity.getText1());
                newEntity.setText2(entity.getText2());
                newEntity.setText3(entity.getText3());
                newEntity.setText4(entity.getText4());
 
                newEntity.setStrobe(entity.getStrobe());
                
                newEntity=repository.save(newEntity);
                
                
              //Converting long to string
            	String clientIdString=String.valueOf(entity.getPriznak());
            	
            	//Creating the log
            	repositoryLogs.generateLog("CMS Update","CMS parameter ID  is "+ entity.getParamid(),clientIdString);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deleteCmsParameterById(Long id) throws RecordNotFoundException 
    {
        Optional<CmsParametersEntity> cmsParameter=repository.findById(id);
         
        if(cmsParameter.isPresent()) 
        {
            repository.deleteById(id);
            
          //Converting long to string
        	String clientIdString=String.valueOf(id);
        	
        	//Creating the log
        	repositoryLogs.generateLog("Deleting CMS Parameter","CMS parameter ID  is being deleted...",clientIdString);
             
            
        } else {
            throw new RecordNotFoundException("No CMS Parameter record exist for given id");
        }
    }
    
    public List<CmsParametersEntity> findByPriznak(String priznak)
    {
    	return repository.findByPriznak(priznak);
    	
    }
    
    public CmsParametersEntity findByDescription(String priznak)
    {
    	return repository.findByDescription(priznak);
    	
    }

}
