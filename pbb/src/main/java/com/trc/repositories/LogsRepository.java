package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.LogsEntity;

@Repository
public interface LogsRepository extends CrudRepository<LogsEntity,Long>   
{
	List<LogsEntity> findBySubject(String subject);
	
	@Query(value="Select * From payments Where clientId=?1 Order By dateCreation Desc",nativeQuery=true)
    List<LogsEntity> getLogsByDateRange(String finalDate, String initialDate);
	
	@Modifying
	@Transactional
	@Query(value="Insert into logsRegister (subject,description,clientId) values(?1,?2,?3)",nativeQuery=true)
    void generateLog(String subject,String description,String clientId);
	
	@Query(value="Select * From logsRegister Where (dateLog<=?1 and dateLog>=?2) Order By dateLog",nativeQuery=true)
    List<LogsEntity> getLogsView(String finalDate, String initialDate);
	
}
