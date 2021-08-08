package com.trc.controllers;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trc.config.MyDataException;
import com.trc.config.RecordNotFoundException;
import com.trc.entities.CategoryEntity;
import com.trc.entities.SettingEntity;
import com.trc.services.APIservices;
import com.trc.services.CategoryServices;
import com.trc.services.FileServicesLogo;
import com.trc.services.LogsServices;
import com.trc.services.SettingServices;


@Controller
@RequestMapping("/pbb/categories")
public class CategoryController 
{
	@Autowired
    CategoryServices service;
	
	@Autowired
	FileServicesLogo fileServicesLogo;
	
	@Autowired
    LogsServices logsServices;
	
	@Autowired
    APIservices serviceAPI;
	
	@Autowired
    SettingServices serviceSettings;
	
		
	@RequestMapping("/list")
    public String getAllCategories(Model model) 
    {
        int total=0;
		
		List<CategoryEntity> list=service.getAllCategories();
        
        //Counting items
		total=list.size();
        
		model.addAttribute("total",total);
        model.addAttribute("categories",list);
        return "categoriesList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editCategoryById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	String nextCategoryNum=null;
    	
        if (id.isPresent()) 
        {
            CategoryEntity entity=service.getCategoryById(id.get());
            model.addAttribute("category",entity);
        } else 
        {
        	CategoryEntity entity=new CategoryEntity();
        	
        	nextCategoryNum=service.createNextCategoryNum();
        	
        	entity.setCategoryNum(nextCategoryNum);
        	
            model.addAttribute("category",entity);
            
        }
        return "categoriesAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteCategoryById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) 
    throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting category","Category was "+ idString,username);
    	
        service.deleteCategoryById(id);
        
        return "redirect:/pbb/categories/list";
    }
 
    @RequestMapping(path="/createCategory", method=RequestMethod.POST)
    public String createOrUpdateCategory(CategoryEntity category,HttpServletRequest servlet) 
    {
    	String categoryName=null;
    	String username=null;
    	
        service.createOrUpdateCategory(category);
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Getting category name
		categoryName=category.getCategoryName();
		
		//Creating log
		logsServices.createLog("Admin creating/updating category","Category is "+ categoryName,username);
    	
        
        return "redirect:/pbb/categories/list";
    }

//Logo processing
    
    @RequestMapping(path={"/IconUpload/{id}"})
    public String uploadStoreLogo(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	    	    	
        CategoryEntity entity=service.getCategoryById(id.get());
                        
        model.addAttribute("category",entity);
                
        //System.out.println("Priznak dir full is "+ priznakDirFull);
        
        return "categoriesUpload";
    }
    
    
    //Upload icons
    
    @PostMapping("/UploadIcon")
    public String uploadLogo(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") Long id, RedirectAttributes redirectAttributes,HttpServletRequest servlet) throws RecordNotFoundException 
    {    		

    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	if(file.getOriginalFilename().equals(""))
    	{	
    		
    		CategoryEntity entity=service.getCategoryById(id);
	    	
	    	model.addAttribute("category",entity);
	    	model.addAttribute("id",id);
	    	model.addAttribute("message","Error: You need to select a file to updload. This selection cannot be empty...");
    		
	    	return "categoriesRedirect";
    		
    	}	
    	    	
        fileServicesLogo.uploadIcon(file,id);
        redirectAttributes.addFlashAttribute("message","Your logo was successfully uploaded "+ file.getOriginalFilename() +"!");

        CategoryEntity entity=service.getCategoryById(id);
        
        //Retriving username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin Uploading new icon for category","Category ID is "+ idString,username);
        
    	
    	model.addAttribute("category",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The logo was uploaded successfully...");
        	        
    	return "categoriesRedirect";
    }

    //Delete icons
    
    @PostMapping("/DeleteIcon")
    public String deleteIcons(Model model,@RequestParam Long id,@RequestParam String icon,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String idString=null;
    	
    	idString=String.valueOf(id);
    	
    	String username=(String) servlet.getSession().getAttribute("name");
    	
    	//Deleting the picture from folder and the picture name from the table
    	fileServicesLogo.deleteIcon(icon,id);
    	
    	CategoryEntity entity=service.getCategoryById(id);
    	
    	//Creating log
    	logsServices.createLog("Admin deleting icon for category","Category ID is"+ idString,username);
    	
    	
    	model.addAttribute("category",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The icon was deleted successfully...");
    	
        return "categoriesRedirect";
    }
    
    
    //Identity Metadata process
    
    @RequestMapping(path={"/apiIdentityForm/{id}"})
    public String apiMetadataForm(Model model, @PathVariable("id") Long id) throws RecordNotFoundException 
    {
    	    	    	
        CategoryEntity entity=service.getCategoryById(id);
                        
        model.addAttribute("category",entity);
                
        //System.out.println("Category ID is "+ id);
        
        return "apiIdentityForm";
    }
    
    @PostMapping("/apiIdentityUpdate")
	public String apiIdentityUpdate(Model model,String categoryName,String identityMetaData,String categoryId)
	{
    	SettingEntity entity=null;
		
		String sname="hugeThesaurusAPIkey";
		String path=null;
		String apiKey=null;
		String podcherk="/";
		String fullPath=null;
		String pustoy=" ";
		String result="";
		
		Long longId=null;
		
		//Converting string to long
		longId=Long.parseLong(categoryId);
		
		//Retrieving synonyms API 
    	entity=serviceSettings.getAPIkey(sname);
    	
    	//Creating the url to the API REST service
    	path=entity.getPath();
    	apiKey=entity.getParam1();
    	
    	fullPath=path+apiKey+podcherk+categoryName+podcherk;
    	
    	try
    	{
    	
    		//Trying to fetch the synonyms from the url
    		result=serviceAPI.getSynonyms(fullPath);
    	
    	}catch(Exception e)
    	{
    		throw new MyDataException("Failed to retrieve data from API source...");
    	
    	}
    	    	    	
    	//Concatenating the old identity definitions string with the new one
    	identityMetaData=identityMetaData+pustoy+result;
    	
    	//Saving changes
    	service.updateIdentityMetaData(longId,identityMetaData);
    	
    	model.addAttribute("categoryId",categoryId);
    	model.addAttribute("stringSearch",categoryName);
    	model.addAttribute("result",result);
    	
    	return "apiIdentityRedirect";
	}
    
    @ExceptionHandler(MyDataException.class)
    public String handleError(Model model,MyDataException e) 
    {

    	model.addAttribute("stringSearch",e);
    	    	
        return "showAPIretrievingError";
    }

    
}
