package com.trc.services;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.trc.repositories.CategoryRepository;
import com.trc.repositories.SettingRepository;

@Service
public class FileServicesLogo 
{
	@Autowired
    CategoryRepository repository;
	
	@Autowired
    SettingRepository repositorySetting;
	
	
	//@Value("${app.upload.dir:${user.home}}")
    public String uploadDirAux="";
    public String uploadDir="";
	public String idString="";

	
	//Uploading the category logo
    public void uploadIcon(MultipartFile file,Long id) 
    {
    	String fileName=null;
    	String podcherk="/";
    	String logosCategories="logosCategories";
    	String images="images";
    	
    	//Getting the Application Path
    	uploadDirAux=repositorySetting.getAppPath("appHomeLogos");
    	
    	
    	idString=String.valueOf(id);
    	    	
    	//System.out.println("Inside the File Service the value of id is: "+ id);
    	//System.out.println("Inside the File Service the value of idString is: "+ idString);
    	
    	uploadDir=uploadDirAux+images+podcherk+logosCategories;
    	
    	//Trying to get the file name
    	fileName=StringUtils.cleanPath(file.getOriginalFilename());
    	
    	//Trying to save the file name
    	
    	repository.updatingLogoName(fileName,id);
    	
    	System.out.println("Inside the Category Logo Service the name of the uploaded file is: "+ fileName);
    	
    	try 
        {
            Path copyLocation=Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e) 
        {
            e.printStackTrace();
            throw new FileSystemNotFoundException("Could not store file "+ file.getOriginalFilename() +". Please try again!");
        }
        
     }
    
  //Deleting store logo
    public void deleteIcon(String picture,Long id) 
    {
    	String dirPath="";
    	String fullPath=null;
    	String podcherk="/";
    	String logosCategories="logosCategories";
    	String images="images";
    	
    	    	
    	//Converting id long to id String
    	idString=String.valueOf(id);
    	
    	//Getting the Application Path
    	dirPath=repositorySetting.getAppPath("appHomeLogos");
    	
    	fullPath=dirPath+images+podcherk+logosCategories+podcherk+picture;
    	    	
    	try
    	{
    		
    		File file=new File(fullPath);
        	
    		if(file.delete())
    		{
    			System.out.println(file.getName() + " has been deleted!");
    			
    			//Trying to delete the line picture name from the table posts
        		repository.deletingIconName(id);
    		}
    		else
    		{
    			System.out.println("Delete operation have failed...");
    		}
    	    		
    	}
    	catch(Exception e)
    	{
    		
    		e.printStackTrace();
    		
    	}
        	
    }
    
	
	
}
