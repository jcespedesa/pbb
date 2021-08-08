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
import com.trc.entities.SettingEntity;
import com.trc.services.LogsServices;
import com.trc.services.SettingServices;


@Controller
@RequestMapping("/pbb/Settings")
public class SettingController 
{
	@Autowired
    SettingServices service;
	
	@Autowired
    LogsServices logsServices;
	
		
	@RequestMapping("/list")
    public String getAllSettings(Model model) 
    {
        List<SettingEntity> list=service.getAllSettings();
        
        //System.out.println("Inside the settings controller!");
 
        model.addAttribute("settings",list);
        return "settingsList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editSettingById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        if(id.isPresent()) 
        {
            SettingEntity entity=service.getSettingById(id.get());
            model.addAttribute("setting",entity);
        } else 
        {
            model.addAttribute("setting",new SettingEntity());
        }
        return "settingsAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteSettingById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) 
    throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting setting parameter","Setting parameter was "+ idString,username);
    	
        service.deleteSettingById(id);
        
        return "redirect:/pbb/Settings/list";
    }
 
    @RequestMapping(path="/createSetting", method=RequestMethod.POST)
    public String createOrUpdateSetting(SettingEntity setting,HttpServletRequest servlet) 
    {
    	String cname=null;
    	String username=null;
    	    	
        service.createOrUpdateSetting(setting);
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Getting category name
		cname=setting.getSname();
		
		//Creating log
		logsServices.createLog("Admin creating/updating setting parameter","Setting parameter is "+ cname,username);
        
        return "redirect:/pbb/Settings/list";
    }


}
