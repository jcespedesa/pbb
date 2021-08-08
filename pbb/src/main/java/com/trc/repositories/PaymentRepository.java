package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.PaymentEntity;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity,Long>  
{
	PaymentEntity findByType(String type);
	
	PaymentEntity findByChargeId(String token);
	
	@Query("select u from PaymentEntity u where u.clientId=?1")
	List<PaymentEntity> getPaymentsByClientId(@Param("clientId") String clientId);
	
	
	@Query(value="select top 1 * from payments where clientid=?1 Order by dateCreation Desc",nativeQuery=true)
	PaymentEntity findLastPayment(@Param("clientId") Long clientId);
	
	@Query(value="Select dateCreation From payments Where clientId=?1 Order By dateCreation" ,nativeQuery=true)
    List<String> findLastSubscriptionDate(String clientId);
	
	@Query(value="Select * From payments Where (dateCreation<=?1 and dateCreation>=?2) Order By dateCreation",nativeQuery=true)
    List<PaymentEntity> getPaymentsView(String finalDate, String initialDate);
	
}
