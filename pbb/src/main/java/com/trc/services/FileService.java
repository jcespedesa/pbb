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

import com.trc.repositories.ClientRepository;
import com.trc.repositories.SettingRepository;


@Service
public class FileService 
{
	@Autowired
    ClientRepository repository;
	
	@Autowired
    SettingRepository repositorySetting;
	
	
	//@Value("${app.upload.dir:${user.home}}")
    public String uploadDirAux="";
    public String uploadDir="";
	public String idString="";

	
	//Uploading the store logo
    public void uploadFile(MultipartFile file,Long id) 
    {
    	String fileName=null;
    	String podcherk="/";
    	String logo="logo";
    	
    	//Getting the Application Path
    	uploadDirAux=repositorySetting.getAppPath("appHome");
    	
    	
    	idString=String.valueOf(id);
    	    	
    	//System.out.println("Inside the File Service the value of id is: "+ id);
    	//System.out.println("Inside the File Service the value of idString is: "+ idString);
    	
    	uploadDir=uploadDirAux+idString+podcherk+logo;
    	
    	//Trying to get the file name
    	
    	fileName=StringUtils.cleanPath(file.getOriginalFilename());
    	
    	//Trying to save the file name
    	
    	repository.updatingPictureName(fileName,id);
    	
    	//System.out.println("Inside the File Service the name of the uploaded file is: "+ fileName);
    	
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
    
    public String filesCounter(Long id) 
    {
    	int fileCount=0;
    	String priznakDirFull="No";
    	String rasdelitel="/";
    	String logo="logo";
    	
    	idString=String.valueOf(id);
    	
    	
    	//Getting the Application Path
    	uploadDirAux=repositorySetting.getAppPath("appHome");
    	
    	uploadDir=uploadDirAux+idString+rasdelitel+logo;
    	
    	try 
        {
    		File directory=new File(uploadDir);
    	    fileCount=directory.list().length;
    	    //System.out.println("File Count: "+fileCount);
    	    
        }catch(Exception e) 
        {
            e.printStackTrace();
            throw new FileSystemNotFoundException("Critical Error: cannot count files inside path: "+ uploadDir +". Please call your system administrator");
        }
    	
    	if(fileCount>0)
    		priznakDirFull="Yes";
    	
    	return priznakDirFull;
    	
    }
	
    
    //Deleting store logo
    public void deleteLogo(String picture,Long id) 
    {
    	String dirPath="";
    	String fullPath=null;
    	String podcherk="/";
    	String logo="logo";
    	
    	
    	//Converting id long to id String
    	idString=String.valueOf(id);
    	
    	//getting the application path
    	dirPath=repositorySetting.getAppPath("appHome");
    	
    	fullPath=dirPath+idString+podcherk+logo+podcherk+picture;
    	    	
    	try
    	{
    		
    		File file=new File(fullPath);
        	
    		if(file.delete())
    		{
    			//System.out.println(file.getName() + " has been deleted!");
    			
    			//Trying to delete the line picture name from the table posts
        		repository.deletingPictureName(id);
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
    
    //Subroutines for pictures
    
    public String picturesCounter(Long id) 
    {
    	int fileCount=0;
    	String priznakDirFull="No";
    	String rasdelitel="/";
    	String folderName="images";
    	
    	idString=String.valueOf(id);
    	
    	
    	//Getting the Application Path
    	uploadDirAux=repositorySetting.getAppPath("appHome");
    	
    	uploadDir=uploadDirAux+idString+rasdelitel+folderName;
    	
    	try 
        {
    		File directory=new File(uploadDir);
    	    fileCount=directory.list().length;
    	    System.out.println("File Count: "+fileCount);
    	    
        }catch(Exception e) 
        {
            e.printStackTrace();
            throw new FileSystemNotFoundException("Critical Error: cannot count files inside path: "+ uploadDir +". Please call your system administrator");
        }
    	
    	if(fileCount>=8)
    		priznakDirFull="Yes";
    	
    	return priznakDirFull;
    	
    }
    
    
    //Uploading store picture
    public void uploadProductPic(MultipartFile file,Long storeId,Long productId) 
    {
    	String fileName=null;
    	String podcherk="/";
    	String images="images";
    	String storeIdString=null;
    	
    	
    	//Getting the Application Path
    	uploadDirAux=repositorySetting.getAppPath("appHome");
    	
    	storeIdString=String.valueOf(storeId);
    	
    	    	
    	//System.out.println("Inside the File Service the value of id is: "+ id);
    	//System.out.println("Inside the File Service the value of idString is: "+ idString);
    	
    	uploadDir=uploadDirAux+storeIdString+podcherk+images;
    	
    	//Trying to get the file name
    	
    	fileName=StringUtils.cleanPath(file.getOriginalFilename());
    	
    	//Trying to save the file name
    	
    	repository.updatingProductPic(fileName,productId);
    	
    	System.out.println("Inside the File Service the name of the uploaded product picture file is: "+ fileName);
    	
    	try 
        {
            Path copyLocation=Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e) 
        {
            e.printStackTrace();
            throw new FileSystemNotFoundException("Could not store product picture file "+ file.getOriginalFilename() +". Please try again!");
        }
        
     }

}
