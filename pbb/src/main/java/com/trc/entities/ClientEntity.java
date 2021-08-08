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
@Table(name="clients")

public class ClientEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long clientid;
     
    @Column(name="cname")
    private String cname;
     
    @Column(name="pass")
    private String pass;
     
    @Column(name="email")
    private String email;
    
    @Column(name="phone")
    private String phone;
    
    
    @Column(name="subscriptionnum")
    private String subscriptionNum;
    
    @Column(name="businessname")
    private String businessName;
    
    @Column(name="url")
    private String url;
    
    @Column(name="logo")
    private String logo;
        
    @Column(name="categoryname")
    private String categoryName;
    
    @Column(name="categorynum")
    private String categoryNum;
    
    @Column(name="numimages")
    private String numImages;
    
    @Column(name="promoname")
    private String promoName;
    
    @Column(name="contact")
    private String contact;
    
    @Column(name="active")
    private String active;
    
    @Column(name="bizdescription")
    private String bizDescription;
    
    @Column(name="country")
    private String country;
    
    @Column(name="state")
    private String state;
    
    @Column(name="city")
    private String city;
    
    @Column(name="address")
    private String address;
    
    @Column(name="zip")
    private String zip;
    
    @Column(name="subsinitialdate")
    private String subsInitialDate;
    
    @Column(name="subsfinaldate")
    private String subsFinalDate;
    
    @Column(name="lat")
    private String lat;
    
    @Column(name="lon")
    private String lon;
    
    @Column(name="latlon")
    private String latlon;
    
    @Column(name="status")
    private String status;
    
    @Column(name="subsfullpayment")
    private String subsFullPayment;
    
    @Column(name="logofilename")
    private String logoFileName;
    
    @Column(name="lastchargeid")
    private String lastChargeId;
    
    @Column(name="daysbetween")
    private String daysBetween;
    
    @Column(name="buffer1")
    private String buffer1;
    
    @Column(name="buffer2")
    private String buffer2;
    
    @Override
    public String toString() 
    {
        return "ClientEntity [clientId="+ clientid +",cname="+ cname +",pass="+ pass +",email="+ email +",phone="+ phone +",subscriptionNum="+ subscriptionNum +",businessName="+ businessName +",url="+ url +""
        		+ ",logo="+ logo +",categoryName="+ categoryName +",numImages="+ numImages +",promoName="+ promoName +",contact="+ contact +",active="+ active +",bizDescription="+ bizDescription +",country="+ country +""
        				+ ",state="+ state +",city="+ city +",address="+ address +",zip="+ zip +",subsInitialDate="+ subsInitialDate +",subsFinalDate="+ subsFinalDate +",lat="+ lat +",lon="+ lon +",latLon="+ latlon +""
        						+ ",status="+ status +",subsFullPayment="+ subsFullPayment +",logoFileName="+ logoFileName +",lastChargeId="+ lastChargeId +",categoryNum="+ categoryNum +",buffer1="+ buffer1 +",buffer2="+ buffer2 +"]";
    }


  //Setters and getters
    
	public Long getClientid() 
	{
		return clientid;
	}


	public void setClientid(Long clientid) 
	{
		this.clientid=clientid;
	}


	public String getCname() 
	{
		return cname;
	}


	public void setCname(String cname) 
	{
		this.cname=cname;
	}


	public String getPass() 
	{
		return pass;
	}


	public void setPass(String pass) 
	{
		this.pass=pass;
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


	public String getSubscriptionNum() 
	{
		return subscriptionNum;
	}


	public void setSubscriptionNum(String subscriptionNum) 
	{
		this.subscriptionNum=subscriptionNum;
	}


	public String getBusinessName() 
	{
		return businessName;
	}


	public void setBusinessName(String businessName) 
	{
		this.businessName=businessName;
	}


	public String getUrl() 
	{
		return url;
	}


	public void setUrl(String url) 
	{
		this.url=url;
	}


	public String getLogo() 
	{
		return logo;
	}


	public void setLogo(String logo) 
	{
		this.logo=logo;
	}


	public String getCategoryName() 
	{
		return categoryName;
	}


	public void setCategoryName(String categoryName) 
	{
		this.categoryName=categoryName;
	}


	public String getNumImages() 
	{
		return numImages;
	}


	public void setNumImages(String numImages) 
	{
		this.numImages=numImages;
	}


	public String getPromoName() 
	{
		return promoName;
	}


	public void setPromoName(String promoName) 
	{
		this.promoName=promoName;
	}


	public String getContact() 
	{
		return contact;
	}


	public void setContact(String contact) 
	{
		this.contact=contact;
	}


	public String getActive() 
	{
		return active;
	}


	public void setActive(String active) 
	{
		this.active=active;
	}


	public String getBizDescription() 
	{
		return bizDescription;
	}


	public void setBizDescription(String bizDescription) 
	{
		this.bizDescription=bizDescription;
	}


	public String getCountry() 
	{
		return country;
	}


	public void setCountry(String country) 
	{
		this.country=country;
	}


	public String getState() 
	{
		return state;
	}


	public void setState(String state) 
	{
		this.state=state;
	}


	public String getCity() 
	{
		return city;
	}


	public void setCity(String city) 
	{
		this.city=city;
	}


	public String getAddress() 
	{
		return address;
	}


	public void setAddress(String address) 
	{
		this.address=address;
	}


	public String getZip() 
	{
		return zip;
	}


	public void setZip(String zip) 
	{
		this.zip=zip;
	}


	public String getSubsInitialDate() 
	{
		return subsInitialDate;
	}


	public void setSubsInitialDate(String subsInitialDate) 
	{
		this.subsInitialDate=subsInitialDate;
	}


	public String getSubsFinalDate() 
	{
		return subsFinalDate;
	}


	public void setSubsFinalDate(String subsFinalDate) 
	{
		this.subsFinalDate=subsFinalDate;
	}


	public String getLat() 
	{
		return lat;
	}


	public void setLat(String lat) 
	{
		this.lat=lat;
	}


	public String getLon() 
	{
		return lon;
	}


	public void setLon(String lon) 
	{
		this.lon=lon;
	}


	public String getLatlon() 
	{
		return latlon;
	}


	public void setLatlon(String latlon) 
	{
		this.latlon=latlon;
	}


	public String getStatus() 
	{
		return status;
	}


	public void setStatus(String status) 
	{
		this.status=status;
	}


	public String getSubsFullPayment() 
	{
		return subsFullPayment;
	}


	public void setSubsFullPayment(String subsFullPayment) 
	{
		this.subsFullPayment=subsFullPayment;
	}


	public String getLogoFileName() 
	{
		return logoFileName;
	}


	public void setLogoFileName(String logoFileName) 
	{
		this.logoFileName=logoFileName;
	}


	public String getLastChargeId() 
	{
		return lastChargeId;
	}


	public void setLastChargeId(String lastChargeId) 
	{
		this.lastChargeId=lastChargeId;
	}


	public String getDaysBetween() 
	{
		return daysBetween;
	}


	public void setDaysBetween(String daysBetween) 
	{
		this.daysBetween=daysBetween;
	}


	public String getCategoryNum() 
	{
		return categoryNum;
	}


	public void setCategoryNum(String categoryNum) 
	{
		this.categoryNum=categoryNum;
	}


	public String getBuffer1() 
	{
		return buffer1;
	}


	public void setBuffer1(String buffer1) 
	{
		this.buffer1=buffer1;
	}

	
	public String getBuffer2() 
	{
		return buffer2;
	}


	public void setBuffer2(String buffer2) 
	{
		this.buffer2=buffer2;
	}

	
}
