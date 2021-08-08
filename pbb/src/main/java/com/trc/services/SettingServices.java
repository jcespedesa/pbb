package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.SettingEntity;
import com.trc.repositories.SettingRepository;


@Service
public class SettingServices 
{
	@Autowired
	SettingRepository repository;
     
    public List<SettingEntity> getAllSettings()
    {
        List<SettingEntity> result=(List<SettingEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<SettingEntity>();
        }
    }
     
    public SettingEntity getSettingById(Long id) throws RecordNotFoundException 
    {
        Optional<SettingEntity> setting=repository.findById(id);
         
        if(setting.isPresent()) 
        {
            return setting.get();
        } 
        else 
        {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public SettingEntity createOrUpdateSetting(SettingEntity entity) 
    {
        if(entity.getSettingid()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<SettingEntity> setting=repository.findById(entity.getSettingid());
             
            if(setting.isPresent()) 
            {
                SettingEntity newEntity=setting.get();
                
                newEntity.setSname(entity.getSname());
                newEntity.setPath(entity.getPath());
                newEntity.setParam1(entity.getParam1());
                newEntity.setParam2(entity.getParam2());
                newEntity.setParam3(entity.getParam3());
                newEntity.setParam4(entity.getParam4());
                 
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
     
    public void deleteSettingById(Long id) throws RecordNotFoundException 
    {
        Optional<SettingEntity> setting=repository.findById(id);
         
        if(setting.isPresent()) 
        {
            repository.deleteById(id);
        } 
        else 
        {
            throw new RecordNotFoundException("No setting record exist for given id");
        }
    }
    
    
    public SettingEntity getAPIkey(String sname)
    {
    	SettingEntity entity=repository.findBySname(sname);
    	
    	return entity;
    }

}
