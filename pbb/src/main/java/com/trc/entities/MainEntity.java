package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cmsparameters")
public class MainEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long paramid;
     
    @Column(name="priznak")
    private String priznak;
    
    @Column(name="param1")
    private String param1;
     
    @Column(name="param2")
    private String param2;
    
    @Column(name="param3")
    private String param3;
     
    @Column(name="param4")
    private String param4;
     
    @Column(name="sentence1")
    private String sentence1;
    
    @Column(name="sentence2")
    private String sentence2;
    
    @Column(name="sentence3")
    private String sentence3;
    
    @Column(name="sentence4")
    private String sentence4;
     
    @Column(name="text1")
    private String text1;
    
    @Column(name="text2")
    private String text2;
    
    @Column(name="text3")
    private String text3;
    
    @Column(name="text4")
    private String text4;
    
    
    @Override
    public String toString() 
    {
        return "MainEntity [paramId="+ paramid +",priznak="+ priznak +",param1="+ param1 +",param2="+ param2 +",param3="+ param3 +",param4="+ param4 +",sentence1="+ sentence1 +",sentence2="+ sentence2 +",sentence3="+ sentence3 +",sentence4="+ sentence4 +",text1="+ text1 +",text2="+ text2 +",text3="+ text3 +",text4="+ text4 +"]";
    }
    
    
    //Setters and getters

	public Long getParamid() 
	{
		return paramid;
	}

	public void setParamid(Long paramid) 
	{
		this.paramid=paramid;
	}

	public String getPriznak() 
	{
		return priznak;
	}

	public void setPriznak(String priznak) 
	{
		this.priznak=priznak;
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

	public String getSentence1() 
	{
		return sentence1;
	}

	public void setSentence1(String sentence1) 
	{
		this.sentence1=sentence1;
	}

	public String getSentence2() 
	{
		return sentence2;
	}

	public void setSentence2(String sentence2) 
	{
		this.sentence2=sentence2;
	}

	public String getSentence3() 
	{
		return sentence3;
	}

	public void setSentence3(String sentence3) 
	{
		this.sentence3=sentence3;
	}

	public String getSentence4() 
	{
		return sentence4;
	}

	public void setSentence4(String sentence4) 
	{
		this.sentence4=sentence4;
	}

	public String getText1() 
	{
		return text1;
	}

	public void setText1(String text1) 
	{
		this.text1=text1;
	}

	public String getText2() 
	{
		return text2;
	}

	public void setText2(String text2) 
	{
		this.text2=text2;
	}

	public String getText3() 
	{
		return text3;
	}

	public void setText3(String text3) 
	{
		this.text3=text3;
	}

	public String getText4() 
	{
		return text4;
	}

	public void setText4(String text4) 
	{
		this.text4=text4;
	}


}
