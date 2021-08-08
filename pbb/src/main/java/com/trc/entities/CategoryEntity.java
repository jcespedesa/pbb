package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="categories")
public class CategoryEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long categoryid;
     
    @Column(name="categorynum")
    private String categoryNum;
     
    @Column(name="categoryname")
    private String categoryName;
     
    @Column(name="icon")
    private String icon;
    
    @Column(name="active")
    private String active;
    
    @Column(name="popularity")
    private String popularity;
    
    @Column(name="identitymetadata")
    private String identityMetaData;
    
    @Column(name="directrelationsmetadata")
    private String directRelationsMetaData;
    
    @Column(name="indirectrelationsmetadata")
    private String indirectRelationsMetaData;
    
    //constructors
   
	public CategoryEntity(Long categoryid, String categoryNum, String categoryName, String icon, String active,
			String popularity, String identityMetaData, String directRelationsMetaData,
			String indirectRelationsMetaData) {
		super();
		this.categoryid=categoryid;
		this.categoryNum=categoryNum;
		this.categoryName=categoryName;
		this.icon=icon;
		this.active=active;
		this.popularity=popularity;
		this.identityMetaData=identityMetaData;
		this.directRelationsMetaData=directRelationsMetaData;
		this.indirectRelationsMetaData=indirectRelationsMetaData;
	}
	
		
	@Override
	public String toString() {
		return "CategoryEntity [categoryid=" + categoryid + ", categoryNum=" + categoryNum + ", categoryName="
				+ categoryName + ", icon=" + icon + ", active=" + active + ", popularity=" + popularity
				+ ", identityMetaData=" + identityMetaData + ", directRelationsMetaData=" + directRelationsMetaData
				+ ", indirectRelationsMetaData=" + indirectRelationsMetaData + "]";
	}

	
	public CategoryEntity()
	{
		
	}

	//Setters and getters

	public Long getCategoryId() 
	{
		return categoryid;
	}

	public void setCategoryId(Long categoryId) 
	{
		this.categoryid=categoryId;
	}

	public String getCategoryNum() 
	{
		return categoryNum;
	}

	public void setCategoryNum(String categoryNum) 
	{
		this.categoryNum=categoryNum;
	}

	public String getCategoryName() 
	{
		return categoryName;
	}

	public void setCategoryName(String categoryName) 
	{
		this.categoryName=categoryName;
	}

	public String getIcon() 
	{
		return icon;
	}

	public void setIcon(String icon) 
	{
		this.icon=icon;
	}

	public String getActive() 
	{
		return active;
	}

	public void setActive(String active) 
	{
		this.active=active;
	}

	public String getPopularity() 
	{
		return popularity;
	}

	public void setPopularity(String popularity) 
	{
		this.popularity=popularity;
	}

	public Long getCategoryid() 
	{
		return categoryid;
	}

	public void setCategoryid(Long categoryid) 
	{
		this.categoryid=categoryid;
	}

	public String getIdentityMetaData() 
	{
		return identityMetaData;
	}

	public void setIdentityMetaData(String identityMetaData) 
	{
		this.identityMetaData=identityMetaData;
	}

	public String getDirectRelationsMetaData() 
	{
		return directRelationsMetaData;
	}

	public void setDirectRelationsMetaData(String directRelationsMetaData) 
	{
		this.directRelationsMetaData=directRelationsMetaData;
	}

	public String getIndirectRelationsMetaData() 
	{
		return indirectRelationsMetaData;
	}

	public void setIndirectRelationsMetaData(String indirectRelationsMetaData) 
	{
		this.indirectRelationsMetaData=indirectRelationsMetaData;
	}
	
	

}
