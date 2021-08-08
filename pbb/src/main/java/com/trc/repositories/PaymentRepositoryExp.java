package com.trc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trc.entities.PaymentEntityExp;

@Repository
public interface PaymentRepositoryExp extends CrudRepository<PaymentEntityExp,Long>  
{
		
	@Query(value="select top 1 * from payments where clientid=?1 Order by dateCreation Desc",nativeQuery=true)
	PaymentEntityExp findLastPaymentExp(@Param("clientId") Long clientId);
	
	//@Query("select u from PaymentsEntityExp u where (u.clientId=?1 and u.chargeId=?2)")
    //PaymentEntityExp getPaymentInfo(String clientId,String chargeId);
		
	@Query(value="select * from payments where clientid=?1 and chargeId=?2",nativeQuery=true)
	PaymentEntityExp getPaymentInfo(String clientId,String chargeId);
	
	@Query(value="Select * From payments Where clientId=?1 Order By dateCreation Desc",nativeQuery=true)
    List<PaymentEntityExp> findPaymentsByClientId(String clientId);
	
	
	
}
