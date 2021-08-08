package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.MainEntity;
import com.trc.entities.MessagesEntity;
import com.trc.services.CmsParametersServices;
import com.trc.services.EmailService;
import com.trc.services.LogsServices;
import com.trc.services.MainService;
import com.trc.services.MessagesService;


@Controller
@RequestMapping("/pbb/messages")
public class MessagesController 
{
	@Autowired
	MessagesService service;
	
	@Autowired
    MainService mainService;
	
	@Autowired
	CmsParametersServices serviceParam;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
    LogsServices logsServices;
	
		
	//CRUD Operations for messages
	
		@GetMapping("/list")
		public String getAllMessages(Model model)
		{
			List<MessagesEntity> list=service.getAllMessagesDesc();
			
			int total=0;
			
			//Counting items
	      	total=list.size();
	              
	      	model.addAttribute("total",total);
			model.addAttribute("messages",list);
			
			return "messagesList";
		}
		
		@RequestMapping(path={"/edit","/edit/{id}"})
	    public String editMessagesById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException
	    {
	        if (id.isPresent()) 
	        {
	            MessagesEntity entity=service.getMessageById(id.get());
	            model.addAttribute("message",entity);
	        } 
	        else 
	        {
	            model.addAttribute("message",new MessagesEntity());
	        }
	        return "messagesAddEdit";
	    }
	    
	    @RequestMapping(path="/delete/{id}")
	    public String deleteMessageById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) throws RecordNotFoundException 
	    {
	    	String idString=null;
	    	String username=null;
	    	
	    	idString=String.valueOf(id);
	    	
	    	//Getting username
	    	username=(String) servlet.getSession().getAttribute("name");
			
			//Creating log
			logsServices.createLog("Admin deleting message","Message was "+ idString,username);
			
	        service.deleteMessageById(id);
	        
	        return "redirect:/pbb/messages/list";
	    }
	 
	    @PostMapping(path="/CreateMessage")
	    public String createOrUpdateMessage(Model model,MessagesEntity message,HttpServletRequest servlet) throws RecordNotFoundException 
	    {
	    	String priznak=null;
	    	String cname=null;
	    	String username=null;
	    	
	        service.createOrUpdateMessage(message);
	       
	      //Getting footer information
			priznak="mainPageMessages";
			MainEntity footerParams=mainService.getByPriznak(priznak);
			
			//Getting username
			username=(String) servlet.getSession().getAttribute("name");
			
			//Getting category name
			cname=message.getCname();
			
			//Creating log
			logsServices.createLog("Admin creating/updating cms parameter","Cms parameter is "+ cname,username);
	        
			model.addAttribute("footerParams",footerParams);
	       	model.addAttribute("message","Your message was sent successfully. We will contact you shortly...");
	    		    	       
	        return "messagesRedirect";
	    }

	    @PostMapping(path="/updateMessage")
	    public String updateMessage(MessagesEntity message,@RequestParam Long id,HttpServletRequest servlet) 
	    {
	    	String outcome=null;
	    	String idString=null;
	    	String username=null;
	    	
	    	service.updateMessage(outcome,id);
	    	
	    	idString=String.valueOf(id);
	    	
	    	//Getting username
	    	username=(String) servlet.getSession().getAttribute("name");
			
			//Creating log
			logsServices.createLog("Admin updating message","Message ID is "+ idString,username);
	    	
	    	outcome=message.getOutcome();
	    		    		    	   	
	        return "redirect:/pbb/messages/list";
	    }
	
	    @PostMapping(path="/emailToFriend")
	    public String emailToFriend(Model model,@RequestParam String email,@RequestParam String message,@RequestParam String website) throws RecordNotFoundException 
	    {
	    	String to=null;
			String subject=null;
			String messageMain=null;
			String priznak=null;
			String newLine=System.getProperty("line.separator");

			
			//System.out.println("Inside the emailToFriend option in the Messages Controller");
			
			to=email;
			subject="A friend of yours is recommending the Peoples Best Buy Web Site";
			messageMain=message;
			
			//Trying to attache the website recommendation
			
			//messageMain=String.join(messageMain,newLine,website);
			
			messageMain=messageMain.concat(newLine).concat(website);
			
			//Trying to send the email
			emailService.sendMail(to,subject,messageMain);
			
			//Getting footer information
			priznak="mainPageMessages";
			MainEntity footerParams=mainService.getByPriznak(priznak);
			
			model.addAttribute("footerParams",footerParams);
			model.addAttribute("message","Your message was sent successfully. Thanks for recommending our site...");
	    	
	       	
	        return "messagesRedirect";
	    }
	    
	    
}
