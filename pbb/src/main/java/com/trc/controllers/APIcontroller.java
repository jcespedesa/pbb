package com.trc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.entities.SettingEntity;
import com.trc.services.APIservices;
import com.trc.services.SettingServices;



@Controller
@RequestMapping("/pbb/api")
public class APIcontroller 
{

	@Autowired
    SettingServices serviceSettings;
	
	@Autowired
    APIservices serviceAPI;
	
	@PostMapping("/synonymsAPI")
	public String apiTest(Model model,String stringSearch)
	{
		SettingEntity entity=null;
		
		String sname="hugeThesaurusAPIkey";
		String path=null;
		String apiKey=null;
		String podcherk="/";
		String fullPath=null;
				
								
		//Retrieving synonyms API 
    	entity=serviceSettings.getAPIkey(sname);
    	
    	//Creating the url to the API REST service
    	
    	path=entity.getPath();
    	apiKey=entity.getParam1();
    	
    	fullPath=path+apiKey+podcherk+stringSearch+podcherk;
    	
    	//Trying to fetch the synonyms from the url
    	
    	String result=serviceAPI.getSynonyms(fullPath);
    	
    	//System.out.println(result);
    	//System.out.println("The url to the API Rest service is "+ fullPath);
		    	
    	model.addAttribute("result",result);
    	model.addAttribute("path",fullPath);
    	model.addAttribute("stringSearch",stringSearch);
	  		
  		return "apiSynonymsRedirect";
		
	}
	
	@RequestMapping("/synonymsAPIsel")
	public String apiSynonymsSel()
	{
		return "apiSynonymsSel";
	}
	
}
