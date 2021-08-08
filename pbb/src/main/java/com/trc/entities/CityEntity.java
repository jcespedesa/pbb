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
@Table(name="cities")
public class CityEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long cityid;
     
    @Column(name="city")
    private String city;
     
    @Column(name="state")
    private String state;
     
    @Column(name="county")
    private String county;
    
        
    @Override
    public String toString() 
    {
        return "CityEntity [cityId="+ cityid +",city="+ city +",state="+ state +",county="+ county +"]";
    }

  //Setters and getters
    
	public Long getCity_id() 
	{
		return cityid;
	}


	public void setCity_id(Long city_id) 
	{
		this.cityid=city_id;
	}


	public String getCity() 
	{
		return city;
	}


	public void setCity(String city) 
	{
		this.city=city;
	}


	public String getState() 
	{
		return state;
	}


	public void setState(String state) 
	{
		this.state=state;
	}


	public String getCounty() 
	{
		return county;
	}


	public void setCounty(String county) 
	{
		this.county=county;
	}

}
