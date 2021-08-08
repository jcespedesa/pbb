package com.trc.controllers;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.entities.ClientEntity;
import com.trc.entities.LogsEntity;
import com.trc.entities.PaymentEntity;
import com.trc.services.ReportServices;


@Controller
@RequestMapping("/pbb/reports")
public class reportsController 
{

	@Autowired
    ReportServices service;
	
	@RequestMapping("/viewCustomers")
    public String viewCustomers(Model model) 
    {
        
		List<ClientEntity> list=service.getCustomersView();
		
		model.addAttribute("clients",list);
		
        return "repCustomersView";
    }
	
	@RequestMapping("/viewLogsSel")
    public String viewLogsSel() 
    {
        
		return "viewLogsSel";
    }
	
	@RequestMapping("/viewPaymentsSel")
    public String viewPaymentsSel() 
    {
        
		return "viewPaymentsSel";
    }
	
	
	//Logs view
	@PostMapping("/logsView")
    public String logsView(Model model,String initialDate,String finalDate) 
    {
	
		List<LogsEntity> list=service.getLogsView(initialDate, finalDate);
		
		//System.out.println("Initial Date was: "+ initialDate);
		//System.out.println("Final Date was: "+ finalDate);
		
		model.addAttribute("logs",list);
		model.addAttribute("initialDate",initialDate);
		model.addAttribute("finalDate",finalDate);
		
		return "viewLogs";
    }
	
	//Payments view
	@PostMapping("/paymentsView")
	public String paymentsView(Model model,String initialDate,String finalDate) 
	{
		String totalPaymentsString=null;
		double totalPayments=0L;
		NumberFormat formatter=NumberFormat.getCurrencyInstance();
		
		List<PaymentEntity> list=service.getPaymentsView(initialDate, finalDate);
			
		//System.out.println("Initial Date was: "+ initialDate);
		//System.out.println("Final Date was: "+ finalDate);
		
		for(PaymentEntity payment : list)
		{
			String paymentString=payment.getAmount();
			double paymentDouble=Double.parseDouble(paymentString);
			
			totalPayments=totalPayments+paymentDouble;
			
		}
		
		if(totalPayments==0)
			totalPaymentsString="0.0";
		else
		{
			totalPaymentsString=formatter.format(totalPayments);
		}
		
		
		model.addAttribute("totalPayments",totalPaymentsString);	
		model.addAttribute("payments",list);
		model.addAttribute("initialDate",initialDate);
		model.addAttribute("finalDate",finalDate);
			
		return "viewPayments";
	}
	
}
