package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="losgsregister")
public class LogsEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long logid;
	
	 @Column(name="description")
	 private String description;
	 
	 @Column(name="subject")
	 private String subject;
	 
	 @Column(name="clientid")
	 private String clientId;
	 
	 @Column(name="datelog")
	 private String dateLog;

	@Override
	public String toString() 
	{
		return "LogsEntity [logid="+ logid +",description="+ description +",subject="+ subject +",clientId="+ clientId +",dateLog="+ dateLog +"]";
	}

	public Long getLogid() 
	{
		return logid;
	}

	public void setLogid(Long logid) 
	{
		this.logid=logid;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description=description;
	}

	public String getSubject() 
	{
		return subject;
	}

	public void setSubject(String subject) 
	{
		this.subject=subject;
	}

	public String getClientId() 
	{
		return clientId;
	}

	public void setClientId(String clientId) 
	{
		this.clientId=clientId;
	}

	public String getDateLog() 
	{
		return dateLog;
	}

	public void setDateLog(String dateLog) 
	{
		this.dateLog=dateLog;
	}
	
	
	 
}


