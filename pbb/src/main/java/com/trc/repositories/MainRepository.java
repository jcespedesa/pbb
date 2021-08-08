package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.MainEntity;

@Repository
public interface MainRepository extends CrudRepository<MainEntity, Long> 
{
	@Query("Select a From MainEntity a Where priznak=?1")
    List<MainEntity> findByPriznak(String priznak);
	
	@Query("Select a From MainEntity a Where priznak=?1")
    MainEntity getByPriznak(String priznak);
	
}
