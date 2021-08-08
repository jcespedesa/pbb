package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Long>   
{
	List<ProductEntity> findProductsByStoreId(String idString);
	
	ProductEntity findById(long id);
	
	@Modifying
	@Transactional
	@Query("update ProductEntity u set u.product=?2,u.price=?3,u.status=?4,u.condition=?5,u.delivery=?6,u.categoryNum=?7 where u.id=?1")
	void updateProductExp(Long id,String product,String price,String status,String condition,String delivery,String categoryNum);
	
	@Query("Select Distinct u.storeId From ProductEntity u Where u.categoryNum=?1")
	List<String> getStoresByCatNum(String categoryNum);
	
	@Modifying
	@Transactional
	@Query("update ProductEntity u set u.categoryNum=?2 where u.storeId=?1")
	void insertCategoryNum(String storeId,String categoryNum);
	
	@Query("Select u.categoryNum From ProductEntity u Where u.categoryNum=?1")
	List<String> getUsedCat(String categoryNum);
	
}
