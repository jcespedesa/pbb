package com.trc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.UserEntity;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<UserEntity,Long>  
{
	UserEntity findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query("update UserEntity u set u.pass=?2 where u.userid=?1")
	void setDefaultPass(Long id,String encodedPass);
	
	
}
