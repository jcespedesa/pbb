 package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.UserEntity;
import com.trc.services.UserServices;


@Controller
@RequestMapping("/pbb/users")

public class UserController 
{
	@Autowired
    UserServices service;
	
	@RequestMapping("/list")
    public String getAllUsers(Model model) 
    {
        List<UserEntity> list=service.getAllUsers();
        
        //System.out.println("Inside the users controller!");
 
        model.addAttribute("users",list);
        return "usersList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editUserById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        if(id.isPresent()) 
        {
            UserEntity entity=service.getUserById(id.get());
            model.addAttribute("user",entity);
        } 
        else 
        {
            model.addAttribute("user",new UserEntity());
        }
        return "usersAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteUserById(Model model, @PathVariable("id") Long id) 
    		throws RecordNotFoundException 
    {
        service.deleteUserById(id);
        return "redirect:/pbb/users/list";
    }
 
    @RequestMapping(path="/createUser", method=RequestMethod.POST)
    public String createOrUpdateUser(UserEntity user) 
    {
        service.createOrUpdateUser(user);
        return "redirect:/pbb/users/list";
    }
    
    @RequestMapping(path={"/resetPass/{id}"})
    public String resetPassById(Model model, @PathVariable("id") Long id) 
    		throws RecordNotFoundException 
    {
    	    	
    	UserEntity entity=service.getUserById(id);
    	    	
        model.addAttribute("user",entity);
                
        return "usersPassReset";
    }

    @RequestMapping(path="/changePass", method=RequestMethod.POST)
    public String changePass(Model model,@RequestParam("id") Long id) throws RecordNotFoundException 
    {
    	service.resetPass(id);
    	
    	return "redirect:/pbb/users/list";
    }
    
    @RequestMapping(path="/setPass", method=RequestMethod.POST)
    public String setPass(Model model,@RequestParam("id") Long id,@RequestParam("newPass") String newPass) throws RecordNotFoundException 
    {
    	service.setPass(id,newPass);
    	
    	return "redirect:/pbb/users/list";
    }
    
    @RequestMapping(path={"/resetMyPass/{username}"})
    public String resetMyPass(Model model, @PathVariable("username") String username) throws RecordNotFoundException 
    {
    	    	
    	UserEntity entity=service.getUserByUsername(username);
    	    	
        model.addAttribute("user",entity);
                
        return "usersPassResetSelf";
    }
    
    @RequestMapping(path="/changeMyPass", method=RequestMethod.POST)
    public String changeMyPass(Model model,@RequestParam("passFrag1") String passFrag1,@RequestParam("passFrag2") String passFrag2,@RequestParam("username") String username) 
    	throws RecordNotFoundException 
    {
    	String message="Password was changed successful...";
    	String priznak="1";
    	
    	if(passFrag1.equals(passFrag2))
    	{	
    		
    		if(passFrag1.length()<6)
    		{	
    			message="Strings are too short..";
    			priznak="0";
    		}
    		
    		if((passFrag1.equals("pbbPassw0rd"))||(passFrag1.equals("Passw0rd")))
    		{	
    			message="Password cannot be the default one..";
    			priznak="0";
    		}
    		
    		
    	}
    	
    	else
    	{	
    	
    		message="Strings are not equals..";
    		priznak="0";
    	
    	 }
    	    	   	
    	//service.resetMyPass(id,pass);
    	
    	model.addAttribute("message",message);
    	model.addAttribute("priznak",priznak);
    	model.addAttribute("username",username);
    	
    	return "usersRedirect";
    }
    

}
