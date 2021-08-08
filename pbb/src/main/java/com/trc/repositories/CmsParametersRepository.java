package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.CmsParametersEntity;

@Repository
public interface CmsParametersRepository extends CrudRepository<CmsParametersEntity,Long>
{
	@Query("Select a From CmsParametersEntity a Where priznak=?1")
	List<CmsParametersEntity> findByPriznak(String priznak);
	
	@Query("Select u From CmsParametersEntity u Where u.priznak=?1")
    CmsParametersEntity findByDescription(String priznak);
}
