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
import com.trc.entities.CmsParametersEntity;
import com.trc.services.CmsParametersServices;
import com.trc.services.LogsServices;


@Controller
@RequestMapping("/pbb/cmsParameters")
public class CmsParametersController 
{
	@Autowired
    CmsParametersServices service;
	
	@Autowired
    LogsServices logsServices;
	
		
	@RequestMapping("/list")
    public String getAllCmsParameters(Model model) 
    {
        List<CmsParametersEntity> list=service.getAllCmsParameters();
 
        model.addAttribute("cmsParameters",list);
        return "cmsParametersList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editCmsParameterById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        if(id.isPresent()) 
        {
        	CmsParametersEntity entity=service.getCmsParameterById(id.get());
            model.addAttribute("cmsParameter",entity);
        } 
        else 
        {
            model.addAttribute("cmsParameter",new CmsParametersEntity());
        }
        return "cmsParametersAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String CmsParameterById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting cms parameter","Cms parameter was "+ idString,username);
		
        service.deleteCmsParameterById(id);
        
        return "redirect:/pbb/cmsParameters/list";
    }
 
    @RequestMapping(path="/createCmsParameter", method=RequestMethod.POST)
    public String createOrUpdateCmsParameters(CmsParametersEntity cmsParameter,HttpServletRequest servlet) 
    {
    	String cmsName=null;
    	String username=null;
    	
        service.createOrUpdateCmsParameter(cmsParameter);
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Getting category name
		cmsName=cmsParameter.getPriznak();
		
		//Creating log
		logsServices.createLog("Admin creating/updating cms parameter","Cms parameter is "+ cmsName,username);
        
        return "redirect:/pbb/cmsParameters/list";
    }

}
