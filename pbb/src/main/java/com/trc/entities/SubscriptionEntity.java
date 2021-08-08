package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="listsubscriptions")
public class SubscriptionEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long subscriptionid;
     
    @Column(name="subscriptionnum")
    private String subscriptionNum;
     
    @Column(name="subscription")
    private String subscription;
     
    @Column(name="amount")
    private String amount;
    
    @Column(name="udelnybes")
    private String udelnyBes;
    
    @Column(name="active")
    private String active;
            
    @Override
    public String toString() 
    {
        return "StateEntity [subscriptionid="+ subscriptionid +",subscriptionnum="+ subscriptionNum +",subscription="+ subscription+",amount="+ amount +",udelnybes="+ udelnyBes +",active="+ active +"]";
    }
    
  //Setters and getters

	public Long getSubscriptionid() 
	{
		return subscriptionid;
	}

	public void setSubscriptionid(Long subscriptionid) 
	{
		this.subscriptionid=subscriptionid;
	}

	public String getSubscriptionNum() 
	{
		return subscriptionNum;
	}

	public void setSubscriptionNum(String subscriptionNum) 
	{
		this.subscriptionNum=subscriptionNum;
	}

	public String getSubscription() 
	{
		return subscription;
	}

	public void setSubscription(String subscription) 
	{
		this.subscription=subscription;
	}

	public String getAmount() 
	{
		return amount;
	}

	public void setAmount(String amount) 
	{
		this.amount=amount;
	}

	public String getUdelnyBes() 
	{
		return udelnyBes;
	}

	public void setUdelnyBes(String udelnyBes) 
	{
		this.udelnyBes=udelnyBes;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}

}
