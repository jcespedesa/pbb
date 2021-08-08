package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="payments")
public class PaymentEntityExp 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long paymentid;
	
	 @Column(name="type")
	 private String type;
	     
	 @Column(name="amount")
	 private String amount;
	    
	 @Column(name="clientid")
	 private String clientId;
	 	 	 		 
	 @Column(name="chargeid")
	 private String chargeId;
	 
	 @Column(name="outcome")
	 private String outcome;
	 
	 @Column(name="datecreation")
	 private String dateCreation;
	 
	 @Override
	 public String toString() 
	 {
	       return "PaymentEntity[paymentid="+ paymentid +",type="+ type +",amount="+ amount +",clientId="+ clientId +",chargeId="+ chargeId +",outcome="+ outcome +",dateCreation="+ dateCreation +"]";
	 }

	//Setters and getters
	 
	public Long getPaymentid() 
	{
		return paymentid;
	}


	public void setPaymentid(Long paymentid) 
	{
		this.paymentid=paymentid;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public String getAmount() 
	{
		return amount;
	}


	public void setAmount(String amount) 
	{
		this.amount=amount;
	}


	public String getClientId() 
	{
		return clientId;
	}


	public void setClientId(String clientId) 
	{
		this.clientId=clientId;
	}


	
	public String getChargeId() 
	{
		return chargeId;
	}


	public void setChargeId(String chargeId) 
	{
		this.chargeId=chargeId;
	}

	public String getOutcome() 
	{
		return outcome;
	}

	public void setOutcome(String outcome) 
	{
		this.outcome=outcome;
	}

	public String getDateCreation() 
	{
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) 
	{
		this.dateCreation=dateCreation;
	}
	
	
		 
}
