package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.StateEntity;
import com.trc.repositories.StateRepository;


@Service
public class StateServices 
{
	@Autowired
	StateRepository repository;
     
    public List<StateEntity> getAllStates()
    {
        List<StateEntity> result=(List<StateEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<StateEntity>();
        }
    }
     
    public StateEntity getStateById(Long id) throws RecordNotFoundException 
    {
        Optional<StateEntity> state=repository.findById(id);
         
        if(state.isPresent()) {
            return state.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public StateEntity createOrUpdateState(StateEntity entity) 
    {
        if(entity.getStateid()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<StateEntity> state=repository.findById(entity.getStateid());
             
            if(state.isPresent()) 
            {
                StateEntity newEntity=state.get();
                
                newEntity.setStateMain(entity.getStateMain());
                newEntity.setState(entity.getState());
                                
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
     
    public void deleteStateById(Long id) throws RecordNotFoundException 
    {
        Optional<StateEntity> state=repository.findById(id);
         
        if(state.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No state record exist for given id");
        }
    } 

}
