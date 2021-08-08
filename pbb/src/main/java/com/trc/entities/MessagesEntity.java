package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messages")
public class MessagesEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
     
    @Column(name="cname")
    private String cname;
    
    @Column(name="email")
    private String email;
     
    @Column(name="phone")
    private String phone;
    
    @Column(name="message")
    private String message;
    
    @Column(name="outcome",insertable=false,updatable=false)
    private String outcome;
    
    @Column(name="strobe",insertable=false,updatable=false)
    private String strobe;
    
    @Column(name="datecreation",insertable=false,updatable=false)
    private String dateCreation;
    
            
    @Override
    public String toString() 
    {
        return "MessagesEntity[id="+ id +",cname="+ cname +",email="+ email +",phone="+ phone +",message="+ message +",outcome="+ outcome +",strobe="+ strobe +",dateCreation="+ dateCreation +"]";
    }

    //Setters and getters
    
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id=id;
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

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message=message;
	}

	public String getOutcome() 
	{
		return outcome;
	}

	public void setOutcome(String outcome) 
	{
		this.outcome=outcome;
	}

	public String getStrobe() 
	{
		return strobe;
	}

	public void setStrobe(String strobe) 
	{
		this.strobe=strobe;
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
