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
@Table(name="secure")
public class UserEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userid;
     
    @Column(name="username")
    private String username;
     
    @Column(name="pass")
    private String pass;
     
    @Column(name="dom")
    private String dom;
    
    @Column(name="type")
    private String type;
    
    
    @Column(name="priznak")
    private String priznak;
    
    @Column(name="active")
    private String active;
    
    @Column(name="email")
    private String email;
    
    
        
    @Override
    public String toString() 
    {
        return "UserEntity [userId="+ userid +",username="+ username +",pass="+ pass +",dom="+ dom +",type="+ type +",priznak="+ priznak +",active="+ active +",email="+ email +"]";
    }


  //Setters and getters
    
	public Long getUserid() 
	{
		return userid;
	}


	public void setUserid(Long userid) 
	{
		this.userid=userid;
	}


	public String getUsername() 
	{
		return username;
	}


	public void setUsername(String username) 
	{
		this.username=username;
	}


	public String getPass() 
	{
		return pass;
	}


	public void setPass(String pass) 
	{
		this.pass=pass;
	}


	public String getDom() 
	{
		return dom;
	}


	public void setDom(String dom) 
	{
		this.dom=dom;
	}


	public String getType() 
	{
		return type;
	}


	public void setType(String type) 
	{
		this.type=type;
	}


	public String getPriznak() 
	{
		return priznak;
	}


	public void setPriznak(String priznak) 
	{
		this.priznak=priznak;
	}


	public String getActive() 
	{
		return active;
	}


	public void setActive(String active) 
	{
		this.active=active;
	}


	public String getEmail() 
	{
		return email;
	}


	public void setEmail(String email) 
	{
		this.email=email;
	}

}
