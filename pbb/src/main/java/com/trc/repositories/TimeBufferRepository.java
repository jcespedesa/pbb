package com.trc.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.trc.entities.TimeBufferEntity;

@Repository
public interface TimeBufferRepository extends CrudRepository<TimeBufferEntity,Long>   
{
	
	@Query(value="Insert into timeBuffer2 (username) values(?1)",nativeQuery=true)
	void createTimeBufferRecord(String username);

	@Query("Select u.todayDate from TimeBufferEntity  u where u.username=?1")
 	String findTodayDate(String username);
	
	@Modifying
	@Transactional
	@Query("Delete from TimeBufferEntity u where u.username=?1")
    void deleteRecord(String username);
	
	
}
