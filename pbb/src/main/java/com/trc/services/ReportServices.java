package com.trc.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ClientEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.PaymentEntity;
import com.trc.repositories.ClientRepository;
import com.trc.repositories.LogsRepository;
import com.trc.repositories.PaymentRepository;

@Service
public class ReportServices 
{

	@Autowired
	ClientRepository repository;
	
	@Autowired
	LogsRepository repositoryLogs;
	
	@Autowired
	PaymentRepository repositoryPayments;
	
	public List<ClientEntity> getCustomersView()
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.getCustomersView();
        
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        
        LocalDate localDateRaw=LocalDate.now();
        
        String todayDate=localDateRaw.toString();
        String daysBetweenString=null;
        
        todayDate=todayDate+" 00:00:00.0";
        
        LocalDate todayDateDate=LocalDate.parse(todayDate,dtf);      
         
        if(result.size() > 0) 
        {
        	for(ClientEntity client : result)
        	{	
        		
        		LocalDate finalDate=LocalDate.parse(client.getSubsFinalDate(),dtf);
        		
        		Long daysBetween=ChronoUnit.DAYS.between(todayDateDate,finalDate);
        		
        		
        		
        		if(daysBetween>=0)
        		{	
        			//System.out.println("The difference in days is : "+ daysBetween);
        			daysBetweenString=String.valueOf(daysBetween);
        			client.setDaysBetween(daysBetweenString);
        			
        		}
        		
        	}	
            return result;
        } 
        else 
        {
        	
        	
            return new ArrayList<ClientEntity>();
        }
    }
	
	public List<LogsEntity> getLogsView(String initialDate, String finalDate)
    {
	
		List<LogsEntity> result=(List<LogsEntity>) repositoryLogs.getLogsView(finalDate,initialDate);
		
		return result;
		
    }
	
	public List<PaymentEntity> getPaymentsView(String initialDate, String finalDate)
    {
	
		List<PaymentEntity> result=(List<PaymentEntity>) repositoryPayments.getPaymentsView(finalDate,initialDate);
		
		return result;
		
    }
	
}
