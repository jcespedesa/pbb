package com.trc.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Synonymus 
{

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long synonymusId;
     
    private String synonymusString;
    
    //Constructors

	public Synonymus(Long synonymusId, String synonymusString) {
		super();
		this.synonymusId=synonymusId;
		this.synonymusString=synonymusString;
	}
	
	public Synonymus()
	{
		
	}
		
	//Setters and getters

	public Long getSynonymusId() {
		return synonymusId;
	}

	public void setSynonymusId(Long synonymusId) {
		this.synonymusId=synonymusId;
	}

	public String getSynonymusString() {
		return synonymusString;
	}

	public void setSynonymusString(String synonymusString) {
		this.synonymusString=synonymusString;
	}
    
   
	
}
