package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.PaymentEntity;
import com.trc.entities.PaymentEntityExp;
import com.trc.repositories.ClientRepository;
import com.trc.repositories.PaymentRepository;
import com.trc.repositories.PaymentRepositoryExp;
import com.trc.repositories.SettingRepository;


@Service
public class PaymentServices 
{

	@Autowired
	PaymentRepository repository;
	
	@Autowired
	PaymentRepositoryExp repositoryExp;
	
	@Autowired
	SettingRepository repositorySettings;
	
	@Autowired
	ClientRepository repositoryClients;
	
	public List<PaymentEntity> getAllPayments()
    {
        List<PaymentEntity> result=(List<PaymentEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<PaymentEntity>();
        }
    }
     
    public PaymentEntity getPaymentById(Long id) throws RecordNotFoundException 
    {
        Optional<PaymentEntity> payment=repository.findById(id);
         
        if(payment.isPresent()) {
            return payment.get();
        } 
        else 
        {
            throw new RecordNotFoundException("No payment record exist for given id");
        }
    }
     
    public PaymentEntity createOrUpdateProduct(PaymentEntity entity) 
    {
        if(entity.getPaymentid()==null) 
        {
            entity=repository.save(entity);
             
            return entity;
        } 
        else
        {
            Optional<PaymentEntity> payment=repository.findById(entity.getPaymentid());
             
            if(payment.isPresent()) 
            {
            	PaymentEntity newEntity=payment.get();
                
                newEntity.setPaymentid(entity.getPaymentid());
                newEntity.setType(entity.getType());
                newEntity.setAmount(entity.getAmount());
                newEntity.setClientId(entity.getClientId());
                newEntity.setChargeId(entity.getChargeId());
                                
                newEntity=repository.save(newEntity);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deletePaymentById(Long id) throws RecordNotFoundException 
    {
        Optional<PaymentEntity> payment=repository.findById(id);
         
        if(payment.isPresent()) 
        {
            repository.deleteById(id);
        } else 
        {
            throw new RecordNotFoundException("No payment record exist for given id");
        }
    }

		
	public String getSubscriptionPrice() throws RecordNotFoundException 
    {
		String subscriptionPrice=null;
		
        subscriptionPrice=repositorySettings.getParam1("subscriptionPrice");
         
        if(subscriptionPrice==null) 
        {
            
            throw new RecordNotFoundException("No subscription price exist for given priznak");
        } 
        else 
        {
        	return subscriptionPrice;
        }
    }
	
	//Insert new payment (custom)
	
	public String inputPayment(String amount,String clientId,String chargeId,String message) 
    {
				
		String paymentId=null;
		Long paymentIdLong;
		
		PaymentEntity payment=new PaymentEntity();
		payment.setAmount(amount);
		payment.setClientId(clientId);
		payment.setType("Subscription");
		payment.setChargeId(chargeId);
		payment.setOutcome(message);
		
		repository.save(payment);
		
		paymentIdLong=payment.getPaymentid();
		
		//converting long to string
		
		paymentId=Long.toString(paymentIdLong);
		
		return paymentId;
				
    }
	
	public PaymentEntity getPaymentByToken(String token) throws RecordNotFoundException
    {
		
		//System.out.println("Token is "+ token);
		
        PaymentEntity payment=repository.findByChargeId(token);
         
        if(payment==null) 
        {
            
            throw new RecordNotFoundException("No payment details were found by given token");
        } 
        else 
        {
        	return payment;
        }
    }
	
	public PaymentEntity getLastPayment(Long id) throws RecordNotFoundException
    {
		
		//System.out.println("Received ID is "+ id);
		
        PaymentEntity payment=repository.findLastPayment(id);
         
        if(payment==null) 
        {
            
            throw new RecordNotFoundException("No payment list was found by given client");
        } 
        else 
        {
        	return payment;
        }
    }
	
	public PaymentEntityExp getLastPaymentExp(Long id) throws RecordNotFoundException
    {
		
		System.out.println("Received ID is "+ id);
		
        PaymentEntityExp payment=repositoryExp.findLastPaymentExp(id);
         
        if(payment==null) 
        {
            
            throw new RecordNotFoundException("No payment exp list was found by given client");
        } 
        else 
        {
        	return payment;
        }
    }
	
	
	//Inserting register of the payment in table clients
	
		public void setChargeId(String clientId,String chargeId) 
	    {
			Long id;		
			
			id=Long.parseLong(clientId);
			
			repositoryClients.setChargeId(id,chargeId);
					
	    }
		
		//Retrieving chargeId last transaction from table clients
		
		public String getChargeId(Long id,String chargeId) 
		{
								
			String lastChargeId=null;
				
			lastChargeId=repositoryClients.getChargeId(id,chargeId);
					
			return lastChargeId;
				
		}	
			
		//Retrieving payment info
		public PaymentEntityExp getPaymentInfo(Long id,String chargeId) 
	    {
			String clientId=null;
			
			//Converting id long to id string
			
			clientId=Long.toString(id);
					
			PaymentEntityExp payment=repositoryExp.getPaymentInfo(clientId,chargeId);
				
			return payment;
			
	    }		
			
		public List<PaymentEntityExp> findPayments(String id)
	    {
	        List<PaymentEntityExp> result=(List<PaymentEntityExp>) repositoryExp.findPaymentsByClientId(id);
	         
	        if(result.size() > 0) 
	        {
	            return result;
	        } 
	        else 
	        {
	            return new ArrayList<PaymentEntityExp>();
	        }
	    }	

}
