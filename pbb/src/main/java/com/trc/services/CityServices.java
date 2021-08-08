package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.CityEntity;
import com.trc.config.RecordNotFoundException;
import com.trc.repositories.CityRepository;
import com.trc.repositories.CityRepositoryCustomImpl;

@Service
public class CityServices 
{
	@Autowired
	CityRepository repository;
	
	@Autowired
	CityRepositoryCustomImpl repositoryCities;
	
	private List<CityEntity> cities;
     
    public List<CityEntity> getAllCities()
    {
        List<CityEntity> result=(List<CityEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<CityEntity>();
        }
    }
     
    public CityEntity getCityById(Long id) throws RecordNotFoundException 
    {
        Optional<CityEntity> city=repository.findById(id);
         
        if(city.isPresent()) {
            return city.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public CityEntity createOrUpdateCity(CityEntity entity) 
    {
        if(entity.getCity_id()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<CityEntity> city=repository.findById(entity.getCity_id());
             
            if(city.isPresent()) 
            {
                CityEntity newEntity=city.get();
                
                newEntity.setCity(entity.getCity());
                newEntity.setState(entity.getState());
                newEntity.setCounty(entity.getCounty());
                
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
     
    public void deleteCityById(Long id) throws RecordNotFoundException 
    {
        Optional<CityEntity> city=repository.findById(id);
         
        if(city.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No city record exist for given id");
        }
    } 
    
    public List<CityEntity> findByCityName(String city) 
    {

        List<CityEntity> result=cities.stream()
            .filter(x -> x.getCity().equalsIgnoreCase(city))
            .collect(Collectors.toList());

        return result;

    } 
    
    public List<CityEntity> findCitiesByState(String state) 
    {
    	
    	//System.out.println("Selected state was: "+ state);

        List<CityEntity> result=cities.stream()
            .filter(x -> x.getCity().equalsIgnoreCase(state))
            .collect(Collectors.toList());
        
        //System.out.println("Leaving the method findCitiesByState and returning result...");

        return result;

    } 
    
    public List<CityEntity> getCitiesInState(String state) 
    {
    	
    	//System.out.println("Selected state was: "+ state);

        List<CityEntity> result=repositoryCities.getCitiesByState(state);
            

        return result;

    } 

}
