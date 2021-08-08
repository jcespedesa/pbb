package com.trc.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.ClientEntity;


@Repository("clientRepository")
public interface ClientRepository extends CrudRepository<ClientEntity,Long> 
{
	ClientEntity findByEmail(String email);
	
	List<ClientEntity> findByCity(String city);
	
	ClientEntity getClientByClientid(long id);
	
	ClientEntity findById(long id);
	
		
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.logo=?1 where u.id=?2")
	void updatingPictureName(@Param("fileName") String fileName,@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.logo='' where u.id=?1")
	void deletingPictureName(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.pass=?2,u.logo='' where u.id=?1")
	void setDefaultPass(Long id,String encodedPass);
	
	@Query("Select u.pass From ClientEntity u Where u.email=?1")
    String findPassByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("update ProductEntity u set u.fileName=?1 where u.productid=?2")
	void updatingProductPic(@Param("fileName") String fileName,@Param("id") Long id);
	
		
	@Modifying
	@Transactional
	@Query(value="insert into products (product,storeId,fileName,price,status) values('',?1,'',0,'')",nativeQuery=true)
	void createProductsNewStore(@Param("id") String id);
	
	@Query("Select a From ClientEntity a Where a.categoryNum=?1 and a.status='Public'")
    List<ClientEntity> findStoresByNum(String categoryNum);
	
	@Query("Select count(*) From ClientEntity a where a.email=?1")
    int checkDuplicateEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value="insert into clients (email,pass,status) values(?1,?2,'New')",nativeQuery=true)
	void createNewUser(@Param("email") String email,@Param("password") String password);
	
	@Query("Select u.clientid From ClientEntity u Where u.email=?1")
    String retriveNewClientId(String email);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.pass=?1 where u.clientid=?2")
	void changePassword(@Param("password") String password,@Param("id") Long id);
	
	@Query("Select u From ClientEntity u Where (u.status='Public' and u.active='Yes')")
	List<ClientEntity>  findActiveClients();
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.status=?1 where u.clientid=?2")
	void changeStatus(@Param("newStatus") String newStatus,@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.subsFullPayment=?1 where u.clientid=?2")
	void changePaymentInfo(@Param("newInfo") String newInfo,@Param("id") Long id);
	
	@Query("Select u.lastChargeId From ClientEntity u Where (u.clientid=?1 and u.lastChargeId=?2)")
    String getChargeId(Long id,String chargeId);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.lastChargeId=?2 where u.id=?1")
	void setChargeId(@Param("id") Long id,@Param("chargeId") String chargeId);
	
	@Query("Select u.email From ClientEntity u Where u.clientid=?1")
    String findEmail(Long id);
	
	@Query("Select u From ClientEntity u Order by u.businessName")
	List<ClientEntity> getCustomersView();
	
	@Query("Select u From ClientEntity u Where u.businessName LIKE ?1% Order by u.businessName")
	List<ClientEntity> getClientsAlpha(String firstLetterName);
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.active='Yes' where u.id=?1")
	void changeActiveInfo(Long id);
	
	@Query("Select u From ClientEntity u Where u.id=?1")
	ClientEntity  getById(Long storeId);
	
	@Query("Select u From ClientEntity u")
    List<ClientEntity> getListClieCat();
	
	@Modifying
	@Transactional
	@Query("update ClientEntity u set u.pass=?2 where u.email=?1")
	void setPass(String email,String encodedPass);
	
}
