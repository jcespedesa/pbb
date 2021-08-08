package com.trc.repositories;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.CategoryEntity;

@Repository
public interface CategoryRepository  extends CrudRepository<CategoryEntity,Long>  
{
	CategoryEntity findByCategoryName(String categoryName);
	
	@Query("select u from CategoryEntity u Order by u.categoryName")
	List<CategoryEntity> findAllDesc();
	
	@Query("select u from CategoryEntity u Order by u.categoryName")
	List<CategoryEntity> getListByName();
	
	@Query("select u from CategoryEntity u Where u.active='Yes' Order by u.categoryName")
	List<CategoryEntity> findAllActive();
	
	@Query("select u.categoryName from CategoryEntity u where u.categoryNum=?1")
	String getCategoryByNum(@Param("categoryNum") String categoryNum);
	
	@Modifying
	@Transactional
	@Query("update CategoryEntity u set u.icon=?1 where u.id=?2")
	void updatingLogoName(@Param("fileName") String fileName,@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("update CategoryEntity u set u.icon='' where u.id=?1")
	void deletingIconName(@Param("id") Long id);
	
	@Query("select u from CategoryEntity u Order by categoryNum")
	List<CategoryEntity> getListByNum();
	
	@Query("select u.categoryNum from CategoryEntity u")
	List<String> getCategoriesNumList();
	
	@Modifying
	@Transactional
	@Query("update CategoryEntity u set u.identityMetaData=?1 where u.id=?2")
	void updateIdentity(@Param("identityMetaData") String identityMetaData,@Param("id") Long id);
}
