package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.entities.MessagesEntity;


@Repository
public interface MessagesRepository extends CrudRepository<MessagesEntity,Long> 
{
	@Query("Select a From MessagesEntity a Order by dateCreation desc")
	List<MessagesEntity> listByDateDesc();
	
	@Modifying
	@Transactional
	@Query("update MessagesEntity u set u.outcome=:outcome where u.id=:id")
	void updateOutcome(@Param("outcome") String outcome,@Param("id") Long id);
	
	@Query("Select a From MessagesEntity a Order by dateCreation desc")
	List<MessagesEntity> findAllDesc();
}
