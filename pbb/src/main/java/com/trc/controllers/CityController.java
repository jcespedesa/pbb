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
import com.trc.entities.CityEntity;
import com.trc.repositories.CityRepositoryCustomImpl;
import com.trc.services.CityServices;
import com.trc.services.LogsServices;


@Controller
@RequestMapping("/pbb/cities")
public class CityController 
{
	@Autowired
    CityServices service;
	
	@Autowired
	CityRepositoryCustomImpl service2;
	
	@Autowired
    LogsServices logsServices;
	
		
		
	@RequestMapping("/list")
    public String getAllCities(Model model) 
    {
        List<CityEntity> list=service.getAllCities();
        
        //System.out.println("Inside the cities controller!");
 
        model.addAttribute("cities",list);
        return "citiesList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editCityById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        if(id.isPresent()) 
        {
            CityEntity entity=service.getCityById(id.get());
            model.addAttribute("city",entity);
        } else {
            model.addAttribute("city",new CityEntity());
        }
        return "citiesAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteCityById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) 
    		throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting city","City was "+ idString,username);
    	
        service.deleteCityById(id);
        
        return "redirect:/pbb/cities/list";
    }
 
    @RequestMapping(path="/createCity", method=RequestMethod.POST)
    public String createOrUpdateCity(CityEntity city,HttpServletRequest servlet) 
    {
    	String cityName=null;
    	String username=null;
    	
        service.createOrUpdateCity(city);
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Getting category name
		cityName=city.getCity();
		
		//Creating log
		logsServices.createLog("Admin creating/updating city","City is "+ cityName,username);
    	
        
        return "redirect:/pbb/cities/list";
    }
    

}
