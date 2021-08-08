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
@Table(name="states")
public class StateEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long stateid;
     
    @Column(name="statemain")
    private String stateMain;
     
    @Column(name="state")
    private String state;
     
            
    @Override
    public String toString() 
    {
        return "StateEntity [stateId="+ stateid +",stateMain="+ stateMain +",state="+ state +"]";
    }

  //Setters and getters
    
	public Long getStateid() 
	{
		return stateid;
	}


	public void setStateid(Long stateid) 
	{
		this.stateid=stateid;
	}


	public String getStateMain() 
	{
		return stateMain;
	}


	public void setStateMain(String stateMain) 
	{
		this.stateMain=stateMain;
	}


	public String getState() 
	{
		return state;
	}


	public void setState(String state) 
	{
		this.state=state;
	}

}
