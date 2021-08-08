package com.trc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trc.services.ToolsServices;

@Controller
@RequestMapping("/pbb/tools")
public class ToolsController 
{

	@Autowired
    ToolsServices service;
	
	@RequestMapping("/list")
    public String listAllTools(Model model) 
    {
        return "toolsList";
    }
	
	@RequestMapping("/setCategoryNum")
    public String setCategoryNum(Model model) 
    {
        int counter=0;
        
        String message="Updating table products, inserting Category Number to each product";
		
        counter=service.getAllClieCat();
        
        model.addAttribute("counter",counter);
        model.addAttribute("message",message);
		
        return "toolsRedirect";
    }
	
	
}
