package com.trc.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.StateEntity;

@Repository("StateRepository")
public interface StateRepository extends CrudRepository<StateEntity,Long>  
{
	StateEntity findByState(String state);
}
