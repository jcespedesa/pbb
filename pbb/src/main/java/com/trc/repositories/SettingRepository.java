package com.trc.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.SettingEntity;

@Repository
public interface SettingRepository extends CrudRepository<SettingEntity,Long>  
{
	SettingEntity findBySname(String setting);
	
	@Query("select u.path from SettingEntity u where u.sname=?1")
	String getAppPath(@Param("sname") String sname);
	
	@Query("select u.param1 from SettingEntity u where u.sname=?1")
	String getParam1(@Param("sname") String sname);
}
