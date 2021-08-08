package com.trc.repositories;

import javax.transaction.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import com.trc.entities.CityEntity;

@Repository
@Transactional
public class CityRepositoryCustomImpl implements CityRepositoryCustom 
{
	@PersistenceContext
    EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CityEntity> getCitiesByState(String state) 
	{
		//System.out.println("Inside the 'Find cities by state' implementation! during selected state was: "+ state);
		
		Query query=entityManager.createNativeQuery("Select * From cities Where state=?", CityEntity.class);
        query.setParameter(1,state);
        return query.getResultList();
	}
	
}
