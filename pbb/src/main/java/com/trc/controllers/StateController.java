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
import com.trc.entities.StateEntity;
import com.trc.services.LogsServices;
import com.trc.services.StateServices;


@Controller
@RequestMapping("/pbb/states")
public class StateController 
{
	@Autowired
    StateServices service;
	
	@Autowired
    LogsServices logsServices;
	
		
	@RequestMapping("/list")
    public String getAllStates(Model model) 
    {
        List<StateEntity> list=service.getAllStates();
        
        //System.out.println("Inside the states controller!");
 
        model.addAttribute("states",list);
        return "statesList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editStateById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        if (id.isPresent()) {
            StateEntity entity=service.getStateById(id.get());
            model.addAttribute("state",entity);
        } else {
            model.addAttribute("state",new StateEntity());
        }
        return "statesAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteStateById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) 
    		throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting State","State was "+ idString,username);
    	
        service.deleteStateById(id);
                     
        return "redirect:/pbb/states/list";
    }
 
    @RequestMapping(path="/createState", method=RequestMethod.POST)
    public String createOrUpdateState(StateEntity state,HttpServletRequest servlet) 
    {
    	String stateName=null;
    	String username=null;
    	
        service.createOrUpdateState(state);
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Getting category name
		stateName=state.getState();
		
		//Creating log
		logsServices.createLog("Admin creating/updating state","State is "+ stateName,username);
        
        return "redirect:/pbb/states/list";
    }

}
