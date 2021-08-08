package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.SubscriptionEntity;
import com.trc.services.LogsServices;
import com.trc.services.SubscriptionServices;


@Controller
@RequestMapping("/pbb/subscriptions")
public class SubscriptionController 
{
	@Autowired
    SubscriptionServices service;
	
	@Autowired
    LogsServices logsServices;
	
		
	@RequestMapping("/list")
    public String getAllSubscriptions(Model model) 
    {
        List<SubscriptionEntity> list=service.getAllSubscriptions();
        
        //System.out.println("Inside the Subscriptions controller!");
 
        model.addAttribute("subscriptions",list);
        return "subscriptionsList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editSubscriptionById(Model model, @PathVariable("id") Optional<Long> id) 
    		throws RecordNotFoundException 
    {
        if (id.isPresent()) {
            SubscriptionEntity entity=service.getSubscriptionById(id.get());
            model.addAttribute("subscription",entity);
        } else {
            model.addAttribute("subscription",new SubscriptionEntity());
        }
        return "subscriptionsAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteSubscriptionById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) 
    		throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting subscription","Subscription was "+ idString,username);
    	
        service.deleteSubscriptionById(id);
        
        return "redirect:/pbb/subscriptions/list";
    }
 
    @RequestMapping(path="/createSubscription", method=RequestMethod.POST)
    public String createOrUpdateSubscription(SubscriptionEntity subscription,HttpServletRequest servlet) 
    {
    	String subscriptionName=null;
    	String username=null;
    	    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Getting subscription name, client ID
		subscriptionName=subscription.getSubscription();
				
		//Creating log
		logsServices.createLog("Admin creating/updating subscription","State is "+ subscriptionName,username);
    	
        service.createOrUpdateSubscription(subscription);
        
        return "redirect:/pbb/subscriptions/list";
    }

}
