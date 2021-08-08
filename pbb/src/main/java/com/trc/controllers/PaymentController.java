package com.trc.controllers;


import com.stripe.model.Coupon;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trc.services.ClientServices;
import com.trc.services.CmsParametersServices;
import com.trc.services.PaymentServices;
import com.trc.services.StripeService;
import com.trc.commons.Response;
import com.trc.config.RecordNotFoundException;
import com.trc.entities.ClientEntity;
import com.trc.entities.CmsParametersEntity;
import com.trc.entities.PaymentEntity;


@Controller
@RequestMapping("/pbb/payments")
public class PaymentController 
{
	@Autowired
    ClientServices service;
	
	@Autowired
    PaymentServices servicePayments;
	
	@Autowired
    CmsParametersServices serviceParams;
	
		
	@Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    private StripeService stripeService;

    public PaymentController(StripeService stripeService) 
    {
        this.stripeService=stripeService;
    }

   
    @GetMapping("/subscription")
    public String subscriptionPage(Model model) 
    {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "subscription";
    }

    @GetMapping("/charge/{id}")
    public String chargePage(Model model,@PathVariable("id") Optional<Long> id)  throws RecordNotFoundException  
    {
    	String subscriptionPrice=null;
    	Double amount;
    	
    	//Retrieving client info
    	ClientEntity entity=service.getClientById(id.get());
        
    	//Retrieving subscription amount
    	subscriptionPrice=servicePayments.getSubscriptionPrice();
    	
    	//Converting the amount from string to long
    	amount=Double.parseDouble(subscriptionPrice);
    	
    	amount=amount/100;
    	
    	model.addAttribute("amount",amount);
    	model.addAttribute("store",entity);
        model.addAttribute("stripePublicKey",API_PUBLIC_KEY);
        model.addAttribute("subscriptionPrice",subscriptionPrice);
        
        return "clieSubsPayment";
    }
    
    
    

    /*========== REST APIs for Handling Payments ===================*/

    @PostMapping("/create-subscription")
    public @ResponseBody
    Response createSubscription(String email, String token, String plan, String coupon) 
    {
        //validate data
        if (token == null || plan.isEmpty()) 
        {
            return new Response(false, "Stripe payment token is missing. Please, try again later.");
        }

        //create customer first
        String customerId=stripeService.createCustomer(email, token);

        if(customerId==null) 
        {
            return new Response(false, "An error occurred while trying to create a customer.");
        }

        //create subscription
        String subscriptionId=stripeService.createSubscription(customerId, plan, coupon);
        
        if (subscriptionId==null) 
        {
            return new Response(false,"An error occurred while trying to create a subscription.");
        }

        // Ideally you should store customerId and subscriptionId along with customer object here.
        // These values are required to update or cancel the subscription at later stage.

        return new Response(true, "Success! Your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody
    Response cancelSubscription(String subscriptionId) 
    {
        boolean status=stripeService.cancelSubscription(subscriptionId);
        
        if(!status) 
        {
            return new Response(false, "Failed to cancel the subscription. Please, try later.");
        }
        return new Response(true,"Subscription cancelled successfully.");
    }

    @PostMapping("/coupon-validator")
    public @ResponseBody
    Response couponValidator(String code) 
    {
        Coupon coupon=stripeService.retrieveCoupon(code);
        
        if (coupon != null && coupon.getValid()) 
        {
            String details=(coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100) : coupon.getPercentOff() + "%") +
                    " OFF " + coupon.getDuration();
            
            return new Response(true, details);
        } 
        else 
        {
            return new Response(false,"This coupon code is not available. This may be because it has expired or has " +
                    "already been applied to your account.");
        }
    }

    @PostMapping("/create-charge")
    public String createCharge(Model model,String email,String token,int amount,String id) throws RecordNotFoundException 
    {
    	Double amountDouble=0.0;
    	Long idLong=null;
    	Long paymentIdLong=null;
    	
    	String amountString=null;
    	String message=null;
    	String paymentId=null;  	
    	
    	
        //validate data
        if(token == null) 
        {
            message="Stripe payment token is missing. Please, try again later...";
        }

        //create charge
        String chargeId=stripeService.createCharge(email,token,amount); 
        
        
        if(chargeId==null) 
        {
            message="An error occurred while trying to create the charge...";
        }
        else
        {
        	amountDouble=(double)amount/100;
        	amountString=Double.toString(amountDouble); 
        	
        	message="Thanks! Your payment was successful...";
        	
        	// You may want to store charge id along with order information
        	//Creating new record in table payments
        	paymentId=servicePayments.inputPayment(amountString,id,chargeId,message);
        	
        	//Registering payment in table clients
        	servicePayments.setChargeId(id,paymentId);
        	
        	
        	idLong=Long.parseLong(id);
        	paymentIdLong=Long.parseLong(paymentId);
        	
        	//Updating table clients
        	service.paymentInfoChange(idLong,"Yes");
        	service.setActiveAfterPayment(idLong);
        	
        
        }
        
        ClientEntity entity=service.getClientById(idLong);
        PaymentEntity payment=servicePayments.getPaymentById(paymentIdLong);
        
        model.addAttribute("store",entity);
    	model.addAttribute("message",message);
    	model.addAttribute("payment",payment);
               
        return "clieSubsPayRedirect";
              
        
    }
    
    
//Confirming the payment 
    
    @GetMapping("/redirect/{id}")
    public Response confirmPayment(Model model,@PathVariable("id") String idString) throws RecordNotFoundException
    {
    	String message=null;
    	String chargeId=null;
    	
    	Long id;
    	
    	//Converting string id to long
    	id=Long.parseLong(idString);
    	
    	    	
    	System.out.println("Redirected client ID is "+ id);
    	 
    	ClientEntity entity=service.getClientById(id);
    	
    	System.out.println("Retrived client was: "+ entity);
    	
    	   	
    	model.addAttribute("store",entity);
    	model.addAttribute("message",message);
    	
    	
    	//System.out.println("Store is : "+ entity);
    	//System.out.println("Payment is : "+ payment);
    	    	         	
    	return new Response(true, "Success! Your charge id is "+ chargeId);
    } 
    
//Leaving payment page
    
    @GetMapping("/back/{id}")
    public String leavePayment(Model model,@PathVariable("id") Long id)  throws RecordNotFoundException  
    {
    	String priznak="messagesClientsMenu";
    	
    	ClientEntity entity=service.getClientById(id);
    	
    	//Retrieving messages for publishing and un-publishing
    	CmsParametersEntity entityParamMessage=serviceParams.findByDescription(priznak);
    	
    	model.addAttribute("entityMessage",entityParamMessage);
        model.addAttribute("store",entity);
    	
        return "clientsMenu";
    }
    
}
