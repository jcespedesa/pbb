package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="settings")
public class SettingEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long settingid;
	
	@Column(name="sname")
    private String sname;
	
	@Column(name="path")
    private String path;
	
	@Column(name="param1")
    private String param1;

	@Column(name="param2")
    private String param2;
	
	@Column(name="param3")
    private String param3;
	
	@Column(name="param4")
    private String param4;
	
	@Column(name="strobe")
    private int strobe;
	
	//constructors
	
	@Override
    public String toString() 
    {
        return "SettingEntity [settingid="+ settingid +",sname="+ sname +",path="+ path +",param1="+ param1 +",param2="+ param2 +",param3="+ param3 +",param4="+ param4 +",strobe="+ strobe +"]";
    }

	//Setters and getters
	
	public Long getSettingid() 
	{
		return settingid;
	}

	public void setSettingid(Long settingid) 
	{
		this.settingid=settingid;
	}

	public String getSname() 
	{
		return sname;
	}

	public void setSname(String sname) 
	{
		this.sname=sname;
	}

	public String getPath() 
	{
		return path;
	}

	public void setPath(String path) 
	{
		this.path=path;
	}

	public String getParam1() 
	{
		return param1;
	}

	public void setParam1(String param1) 
	{
		this.param1=param1;
	}

	public String getParam2() 
	{
		return param2;
	}

	public void setParam2(String param2) 
	{
		this.param2=param2;
	}

	public int getStrobe() 
	{
		return strobe;
	}

	public void setStrobe(int strobe) 
	{
		this.strobe=strobe;
	}

	public String getParam3() 
	{
		return param3;
	}

	public void setParam3(String param3) 
	{
		this.param3=param3;
	}

	public String getParam4() 
	{
		return param4;
	}

	public void setParam4(String param4) 
	{
		this.param4=param4;
	}
	
}
