package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requests")
public class RequestsEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long requestid;
	
	 @Column(name="storeid")
	 private String storeId;
	 
	 @Column(name="cname")
	 private String cname;
	 
	 @Column(name="email")
	 private String email;
	 
	 @Column(name="phone")
	 private String phone;
	 
	 @Column(name="product")
	 private String product;
	 
	 @Column(name="instructions")
	 private String instructions;
	 
	 //Constructor
	 
	 @Override
	 public String toString() 
	 {
	       return "RequestsEntity[requestid="+ requestid +",storeId="+ storeId +",cname="+ cname +",email="+ email +",phone="+ phone +",product="+ product +",instructions="+ instructions +"]";
	    		   
	    		   
	 }
	 
	 //Getters and setters

	public Long getRequestid() 
	{
		return requestid;
	}

	public void setRequestid(Long requestid) 
	{
		this.requestid=requestid;
	}

	public String getStoreId() 
	{
		return storeId;
	}

	public void setStoreId(String storeId) 
	{
		this.storeId=storeId;
	}

	public String getCname() 
	{
		return cname;
	}

	public void setCname(String cname) 
	{
		this.cname=cname;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email=email;
	}

	public String getPhone() 
	{
		return phone;
	}

	public void setPhone(String phone) 
	{
		this.phone=phone;
	}

	public String getProduct() 
	{
		return product;
	}

	public void setProduct(String product) 
	{
		this.product=product;
	}

	public String getInstructions() 
	{
		return instructions;
	}

	public void setInstructions(String instructions) 
	{
		this.instructions=instructions;
	}
	 
}
