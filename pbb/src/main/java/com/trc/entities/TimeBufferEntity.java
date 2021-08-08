package com.trc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="timebuffer2")
public class TimeBufferEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long timecreationid;
	
	@Column(name="username")
    private String username;
	
	@Column(name="todayDate")
    private Date todayDate;
	
	@Override
    public String toString() 
    {
        return "TimeBufferEntity [timecreationid="+ timecreationid +",username="+ username +",todayDate="+ todayDate +"]";
    }

	public Long getTimecreationid() 
	{
		return timecreationid;
	}

	public void setTimecreationid(Long timecreationid) 
	{
		this.timecreationid=timecreationid;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username=username;
	}

	public Date getTodayDate() 
	{
		return todayDate;
	}

	public void setTodayDate(Date todayDate) 
	{
		this.todayDate=todayDate;
	}

}
