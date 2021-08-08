package com.trc.services;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.ClientEntity;
import com.trc.repositories.CategoryRepository;
import com.trc.repositories.ClientRepository;
import com.trc.repositories.LogsRepository;
import com.trc.repositories.PaymentRepository;
import com.trc.repositories.ProductRepository;
import com.trc.repositories.SettingRepository;
import com.trc.repositories.TimeBufferRepository;

@Service
public class ClientServices 
{
	@Autowired
	ClientRepository repository;
	
	@Autowired
    SettingRepository repositorySetting;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	CategoryRepository repositoryCategory;
	
	@Autowired
	PaymentRepository repositoryPayment;
	
	@Autowired
	LogsRepository repositoryLogs;
	
	@Autowired
	ProductRepository repositoryProducts;
	
	@Autowired
	TimeBufferRepository repositoryTimeBuffer;
     
    public List<ClientEntity> getAllClients()
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<ClientEntity>();
        }
    }
    
    public List<ClientEntity> getAllActiveClients()
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.findActiveClients();
         
        return result;
       
    }
    
    public List<ClientEntity> getClientsAlpha(String firstLetterName)
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.getClientsAlpha(firstLetterName);
        
        return result;
       
    }
    
    
     
    public ClientEntity getClientById(Long id) throws RecordNotFoundException 
    {
        Optional<ClientEntity> client=repository.findById(id);
         
        if(client.isPresent()) 
        {
            return client.get();
        } 
        else 
        {
            throw new RecordNotFoundException("No client record exist for given id");
        }
    }
     
    public ClientEntity createOrUpdateClient(ClientEntity entity) 
    {
    	int i=0;
    	
    	String dirPath="";
    	String idString="";
    	String podcherk="/";
    	String logo="logo";
    	String images="images";
    	String dirPathLogo="";
    	String dirPathImages="";
    	String encodedPass=null;
    	
    	String categoryName=null;
    	
    	
		long idLong=0;
		
		
		//Getting the category description
		categoryName=repositoryCategory.getCategoryByNum(entity.getCategoryNum());
		
		//getting the application path
		dirPath=repositorySetting.getAppPath("appHome");
    	
    	
        if(entity.getClientid()==null) 
        {
        	//Setting up default values
        	entity.setCategoryName(categoryName);
        	entity.setSubsFullPayment("No");
        	        	
        	//Creating a new client
            entity=repository.save(entity);
            
            
            //Retrieving the client ID
            idLong=entity.getClientid();
            
            //Converting the Long value of the ID to String
            idString=Long.toString(idLong);
            
            //Now we will try to create the ten initial records for table products
            //(The one really creating the products list is in createUtilityFolders() function below within this page)
            for(i=0;i<10;i++)
            {
            	repository.createProductsNewStore(idString);
            	
            	//System.out.println("Creating product number: "+ i);
            }
            
            
          //Converting long to string
        	String clientIdString=String.valueOf(idString);
        	
        	//Creating the log
        	repositoryLogs.generateLog("Creating new client","New client is being created...",clientIdString);
            
            
            //Section to create the utilities folders "Images" and "Logo"
            System.out.println("Inserted record ID: " + idString);
            
            dirPath=dirPath+idString;
            
            File file=new File(dirPath);
            
            //Creating client's folder
            
            if(!file.exists()) 
            {
                if(file.mkdir()) 
                {
                    System.out.println("Directory "+ dirPath +" has been created!");
                    
                    //Trying now to create the logo folder
                    dirPathLogo=dirPath+podcherk+logo;
                    
                    File folderLogo=new File(dirPathLogo);
                    
                    if(folderLogo.mkdir()) 
                    {
                    	System.out.println("Logo folder was created successfully!");
                    	
                    }
                    else 
                    {
                        System.out.println("Logo folder failed to be created!");
                    }
                    
                    //Trying now to create the images folder
                    dirPathImages=dirPath+podcherk+images;
                    
                    File folderImages=new File(dirPathImages);
                    
                    if(folderImages.mkdir()) 
                    {
                    	System.out.println("Images folder was created successfully!");
                    	
                    }
                    else 
                    {
                        System.out.println("Images folder failed to be created!");
                    }
                    
                    
                    
                } 
                else 
                {
                    System.out.println("Directory "+ dirPath +" failed to be created!");
                }
            }
            //Encoding the default password
            encodedPass=passwordEncoder.encode("Passw0rd");
            
            //Setting up the default password for the new client
            repository.setDefaultPass(idLong,encodedPass);
                                     
            return entity;
        } 
        else
        {
            Optional<ClientEntity> client=repository.findById(entity.getClientid());
             
            if(client.isPresent()) 
            {
                ClientEntity newEntity=client.get();
                
                newEntity.setCname(entity.getCname());
                newEntity.setPass(entity.getPass());
                newEntity.setEmail(entity.getEmail());
                newEntity.setPhone(entity.getPhone());
                newEntity.setSubscriptionNum(entity.getSubscriptionNum());
                newEntity.setBusinessName(entity.getBusinessName());
                newEntity.setUrl(entity.getUrl());
                
                newEntity.setLogo(entity.getLogo());
                newEntity.setCategoryName(categoryName);
                newEntity.setCategoryNum(entity.getCategoryNum());
                newEntity.setNumImages(entity.getNumImages());
                newEntity.setPromoName(entity.getPromoName());
                newEntity.setContact(entity.getContact());
                newEntity.setActive(entity.getActive());
                newEntity.setBizDescription(entity.getBizDescription());
                
                newEntity.setCountry(entity.getCountry());
                newEntity.setState(entity.getState());
                newEntity.setCity(entity.getCity());
                newEntity.setAddress(entity.getAddress());
                newEntity.setZip(entity.getZip());
                newEntity.setSubsInitialDate(entity.getSubsInitialDate());
                newEntity.setSubsFinalDate(entity.getSubsFinalDate()); 
                
                newEntity.setLat(entity.getLat());
                newEntity.setLon(entity.getLon());
                newEntity.setLatlon(entity.getLatlon());
                
                newEntity.setStatus(entity.getStatus());
                newEntity.setActive(entity.getActive());
                
                newEntity.setLogoFileName(entity.getLogoFileName());
                                               
                newEntity=repository.save(newEntity);
                
              //Converting long to string
            	String clientIdString=String.valueOf(entity.getClientid());
            	
            	//Creating the log
            	repositoryLogs.generateLog("Client Info Update","Client information is getting updated",clientIdString);
                 
                
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deleteClientById(Long id) throws RecordNotFoundException 
    {
        Optional<ClientEntity> client=repository.findById(id);
         
        if(client.isPresent()) 
        {
            repository.deleteById(id);
            
          //Converting long to string
        	String clientIdString=String.valueOf(id);
        	
        	//Creating the log
        	repositoryLogs.generateLog("Client Deletion","Client is being deleted",clientIdString);
            
        } else {
            throw new RecordNotFoundException("No client record exist for given id");
        }
    } 
    
    
    public void changePass(ClientEntity client) throws RecordNotFoundException 
    {
    	    	
    	Optional<ClientEntity> clientOptional=repository.findById(client.getClientid());
        
        clientOptional.ifPresent((ClientEntity clientResult) -> {
        	
        	clientResult.setPass(client.getPass());
        	repository.save(clientResult);
        	
        	
        	
        });
    	
    	
       //System.out.println("New password for client ID: "+ client.getClientid() +" is "+ client.getPass());
    } 
    
    public void resetPass(Long id) 
    {
    	String encodedPass=null;
    	
    	//Trying to set client's password to the default one
    	
    	//Encoding the default password
        encodedPass=passwordEncoder.encode("Passw0rd");
        
          	
    	repository.setDefaultPass(id,encodedPass);
    	
    	//Converting Long to String
    	String clientIdString=String.valueOf(id);
    	
    	//Creating the log
    	repositoryLogs.generateLog("Reseting Password","Admin reseting client password",clientIdString);
    	
    	
    }
    
    //Subroutine to process client login
    
    public String getPassByEmail(String email) 
    {
    	String storedPass="";
    	    	
        storedPass=repository.findPassByEmail(email);
         
        return storedPass;
    }
    
    //Subroutine to find a client by email
    
    public ClientEntity getClientByEmail(String email) 
    {
    	ClientEntity entity=repository.findByEmail(email);
    	    	
                 
        return entity;
    }
    
    
    //Subroutine to update store
    
    public String updateStore(ClientEntity client,long id) 
    {
    	String message="";
    	String categoryName=null;
    	
    	//Getting the category description
    	categoryName=repositoryCategory.getCategoryByNum(client.getCategoryNum());
    	
    	//System.out.println("Inside the service file...");
    	  	    	    	
    	ClientEntity entity=repository.getClientByClientid(id);
    	    	    	
    	entity.setBusinessName(client.getBusinessName());
    	entity.setPhone(client.getPhone());
    	entity.setUrl(client.getUrl());
    	entity.setCountry(client.getCountry());
    	entity.setState(client.getState());
    	entity.setCity(client.getCity());
    	entity.setAddress(client.getAddress());
    	entity.setZip(client.getZip());
    	entity.setCategoryNum(client.getCategoryNum());
    	entity.setCategoryName(categoryName);
    	entity.setBizDescription(client.getBizDescription());
    	
    	entity=repository.save(entity);
    	
    	//Converting Long to String
    	String clientIdString=String.valueOf(id);
    	
    	//Creating the log
    	repositoryLogs.generateLog("Update Profile","Client updating store information",clientIdString);
    	
    	message="Saved...";
         
        return message;
    }
    
    
    public List<ClientEntity> getStoresByNum(String categoryNum)
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.findStoresByNum(categoryNum);
        
            
        return result;
        
    }
    
    
    public List<ClientEntity> getStoresByCatNum(String categoryNum)
    {
    	
    	Long storeIdLong=null;
    	
    	List<ClientEntity> result=new ArrayList<ClientEntity>();
    	
    	//Retrieving list of store IDs having selected category for sale
    	List<String> storeIds=(List<String>) repositoryProducts.getStoresByCatNum(categoryNum);
    	
    	//Retrieving list of stores from their IDs
    	for(String storeId : storeIds)
    	{
    		
    		storeIdLong=Long.parseLong(storeId);
    		
    		ClientEntity entity=repository.getById(storeIdLong);
    		
    		result.add(entity);
    		
    	}
    	
        return result;
        
    }
    
    
    public boolean getDuplicatedEmail(String email) 
    {
    	boolean priznakDuplicateEmail=false; 
    	int priznak=0;
    	
    	priznak=repository.checkDuplicateEmail(email);
    	
    	//System.out.println("The value of priznak is "+ priznak);
    	 
    	if(priznak>0)
    		priznakDuplicateEmail=true;
    	
                 
        return priznakDuplicateEmail;
    }
    
    
    //Creating new user profile
    
    public void createNewUsername(String email,String password)
    {
        
    	repository.createNewUser(email,password);
    	
    	    	
    	//Creating the log
    	repositoryLogs.generateLog("New User Creation","System creating new user",email);
            
    }
    
    //Creating new folders for the new user
    
    public void createUtilityFolders(String email)
    {
    	int i=0; 
    	
    	String idString=null;
    	String dirPath="";
    	String podcherk="/";
    	String logo="logo";
    	String images="images";
    	String dirPathLogo="";
    	String dirPathImages="";
    	    	
        //Retrieving the new client ID
    	idString=repository.retriveNewClientId(email);
    	
    	//getting the application path
    	dirPath=repositorySetting.getAppPath("appHome");
    	
    	//Now we will try to create the seven initial records for table products
        for(i=0;i<10;i++)
        {
        	repository.createProductsNewStore(idString);
        }
    	
      //Section to create the utilities folders "Images" and "Logo"
        System.out.println("Found record ID: " + idString);
        
        dirPath=dirPath+idString;
        
        File file=new File(dirPath);
        
        //Creating client's folder
        
        if(!file.exists()) 
        {
            if(file.mkdir()) 
            {
                System.out.println("Directory "+ dirPath +" has been created!");
                
                //Trying now to create the logo folder
                dirPathLogo=dirPath+podcherk+logo;
                
                File folderLogo=new File(dirPathLogo);
                
                if(folderLogo.mkdir()) 
                {
                	System.out.println("Logo folder was created successfully!");
                	
                }
                else 
                {
                    System.out.println("Logo folder failed to be created!");
                }
                
                //Trying now to create the images folder
                dirPathImages=dirPath+podcherk+images;
                
                File folderImages=new File(dirPathImages);
                
                if(folderImages.mkdir()) 
                {
                	System.out.println("Images folder was created successfully!");
                	
                }
                else 
                {
                    System.out.println("Images folder failed to be created!");
                }
                
              //Creating the log
            	repositoryLogs.generateLog("New Folders Creation","System creating new utility folders",email);
                
            } 
            else 
            {
                System.out.println("Directory "+ dirPath +" failed to be created!");
            }
        }
        
    }
    
    public void changeStorePassword(Long id,String password)
    {
        
    	repository.changePassword(password,id);
            
    }
    
    public void statusChange(Long id,String newStatus)
    {
        
    	repository.changeStatus(newStatus,id);
    	
    	//Converting long to string
    	String clientIdString=String.valueOf(id);
    	
    	//Creating the log
    	repositoryLogs.generateLog("Client Changing Store Status","New status is "+ newStatus,clientIdString);
            
    }
    
    public void paymentInfoChange(Long id,String newInfo)
    {
        
    	repository.changePaymentInfo(newInfo,id);
            
    }
    
    //Subroutine to get a client's email
    public String getEmail(Long id) 
    {
    	String to=repository.findEmail(id);
    	    	
                 
        return to;
    }
    
    //Listing stores by city
    
    public List<ClientEntity> getStoresByCity(String city)
    {
        List<ClientEntity> result=(List<ClientEntity>) repository.findByCity(city);
        
        //System.out.println("Resulting array is: "+ result);
      
        return result;
        
    }
    
    public String getDaysLeftSubscription(Long clientId)
    {
    	List<String> lastSubscriptionList=new ArrayList<String>();
    	
    	String daysLeftSubscription=null;
    	    	
    	String lastSubscription=null;
    	String clientIdString=null;
    	
    	Long daysLeftSubscriptionLong=null;
    	    	
    	Date lastSubscriptionDate=null;
    	Date todayDate=null;
    	
    	//Trying to get today's date
    	todayDate=new Date();
    	
    	
    	//Converting long to string
    	clientIdString=String.valueOf(clientId);
    	
    	//Retrieving last subscription payment date
    	lastSubscriptionList=repositoryPayment.findLastSubscriptionDate(clientIdString);
    	    	
    	    	
    	if(lastSubscriptionList.size()==0)
    		daysLeftSubscription="N/A";
    	
    	else
    	{	
    	
    		//Assigning the last date from the array of subscription dates
        	lastSubscription=lastSubscriptionList.get(lastSubscriptionList.size()-1);
        	
    		
    		//Converting last date string to date
    		try 
    		{
    			lastSubscriptionDate=new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH).parse(lastSubscription);
			//todayDate=new SimpleDateFormat("dd-MM-yyyy").parse(todayDate);
			
    		} catch (ParseException e) 
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    	
    		//System.out.println("Today date is: "+ todayDate);
    		//System.out.println("Date last subscription string is: "+ lastSubscription);
    		//System.out.println("Date last subscription is: "+ lastSubscriptionDate);
    	
    		long diffInMillies=Math.abs(todayDate.getTime() - lastSubscriptionDate.getTime());
    		long diffInDays=TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
                       
    		//System.out.println("Difference in milliseconds is: "+ diffInMillies); 
    		//System.out.println("Difference in days is: "+ diff);  
    		
    		daysLeftSubscriptionLong=365L - diffInDays;
    		
    		if(daysLeftSubscriptionLong<=0)
    			daysLeftSubscription="Expired";
    		
    		else
    			daysLeftSubscription=String.valueOf(daysLeftSubscriptionLong);
        
    	}	
    		
    	return daysLeftSubscription;
    }
    
    //Activating client after payment
    public void setActiveAfterPayment(Long id)
    {
        
    	repository.changeActiveInfo(id);
    	
    	//Converting long to string
    	String clientIdString=String.valueOf(id);
    	
    	//Creating the log
    	repositoryLogs.generateLog("Client Getting Active","Client gets automatically activated after payment",clientIdString);
            
    }
    
    //Finding if the email exists
    
    public boolean emailExist(String email) 
    {
    	boolean priznakEmail=false; 
    	
    	int priznak=0;
    	
    	priznak=repository.checkDuplicateEmail(email);
    	
    	//System.out.println("The value of priznak is "+ priznak);
    	 
    	if(priznak==0)
    		priznakEmail=false;
    	else
    		priznakEmail=true;
                 
        return priznakEmail;
    }
    
//Setting new password by email
    
    public void savingNewPass(String email,String password)
    {
        
    	repository.setPass(email,password);
    	
    	    	
    	//Creating the log
    	repositoryLogs.generateLog("Setting new password","System saving new password by user request",email);
            
    }
    
    
}
