package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.CategoryEntity;
import com.trc.entities.CityEntity;
import com.trc.entities.ClientEntity;
import com.trc.entities.CmsParametersEntity;
import com.trc.entities.PaymentEntityExp;
import com.trc.entities.ProductEntity;
import com.trc.entities.StateEntity;
import com.trc.services.CategoryServices;
import com.trc.services.CityServices;
import com.trc.services.ClientServices;
import com.trc.services.CmsParametersServices;
import com.trc.services.FileService;
import com.trc.services.LogsServices;
import com.trc.services.PaymentServices;
import com.trc.services.ProductServices;
import com.trc.services.StateServices;


@Controller
@RequestMapping("/pbb/clients")
public class ClientController 
{
	@Autowired
    ClientServices service;
	
	@Autowired
	CityServices serviceCities;
	
	@Autowired
	StateServices serviceStates;
	
	@Autowired
	CategoryServices serviceCategories;
	
	@Autowired
	ProductServices serviceProducts;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	PaymentServices servicePayments;
	
	@Autowired
    CmsParametersServices serviceParams;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    LogsServices logsServices;
	
			
	@RequestMapping("/list")
    public String getAllClients(Model model) 
    {
        List<ClientEntity> list=service.getAllClients();
        
        int total=0;
        
        String daysLeftSubscription=null;
        //Long clientId=2059L;
        
        //Retrieving days left with latest subscription for each client
        for(ClientEntity client : list)
        {
        	 daysLeftSubscription=service.getDaysLeftSubscription(client.getClientid());
        	 client.setBuffer1(daysLeftSubscription);
  	
        }
     
        //daysLeftSubscription=service.getDaysLeftSubscription(clientId);
        
        //Counting items
      	total=list.size();
              
      	model.addAttribute("total",total);
        model.addAttribute("clients",list);
        return "clientsList";
    }
 
    @RequestMapping(path={"/edit","/edit/{id}"})
    public String editClientById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	Long localClientId=null;
    	String localLogo=null;
    	String localCategory=null;
    	String clientId=null;
    	String daysLeftSubscription=null;
    	
    	
        if(id.isPresent()) 
        {
            ClientEntity entity=service.getClientById(id.get());
            
            localClientId=entity.getClientid();
            localLogo=entity.getLogo();
            
          //Retrieving products list
            List<ProductEntity> listProducts=serviceProducts.findProductsByStore(id.get());
            
            //Inserting categories description
            for(ProductEntity product : listProducts)
            {
            	localCategory=serviceCategories.getCategoryByNum(product.getCategoryNum());
            	
            	product.setCategory(localCategory);
            }
            
            
            //Converting long client Id to string
            clientId=String.valueOf(localClientId);
            
            //Retrieving payments list
            List<PaymentEntityExp> listPayments=servicePayments.findPayments(clientId);
            
            //Retrieving days left in subscription
            daysLeftSubscription=service.getDaysLeftSubscription(localClientId);
            
            model.addAttribute("client",entity);
            model.addAttribute("localClientId",localClientId);
            model.addAttribute("localLogo",localLogo);
            model.addAttribute("products",listProducts);
            model.addAttribute("payments",listPayments);
            model.addAttribute("daysLeftSubscription",daysLeftSubscription);           
            
        }else 
        {
            model.addAttribute("client",new ClientEntity());
        }
        
        //Preparing the list of cities to be displayed in the form as selection option
        List<CityEntity> listCities=serviceCities.getAllCities();
        model.addAttribute("cities",listCities);
        
      //Preparing the list of states to be displayed in the form as selection option
        List<StateEntity> listStates=serviceStates.getAllStates();
        model.addAttribute("states",listStates);
      
      //Preparing the list of categories to be displayed in the form as selection option
        List<CategoryEntity> listCategories=serviceCategories.getAllCategories();
        model.addAttribute("categories",listCategories);
        
            
        
        return "clientsAddEdit";
    }
    
    @RequestMapping(path="/delete/{id}")
    public String deleteClientById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting client from the system","Client ID was "+ idString,username);
    	
        service.deleteClientById(id);
        
        return "redirect:/pbb/clients/list";
    }
 
    @RequestMapping(path="/createClient", method=RequestMethod.POST)
    public String createOrUpdateClient(ClientEntity client,HttpServletRequest servlet) 
    {
    	String cname=null;
    	String username=null;
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Getting subscription name, client ID
		cname=client.getEmail();
				
		//Creating log
		logsServices.createLog("Admin creating/updating client","Client ID is "+ cname,username);
    	
        service.createOrUpdateClient(client);
        
        return "redirect:/pbb/clients/list";
    }
    
    @RequestMapping(path={"/ResetPass/{id}"})
    public String resetPassById(Model model, @PathVariable("id") Long id,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String idString=null;
    	String username=null;
    	
    	idString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin reseting client`s password","Client ID is "+ idString,username);
    	
    	
    	//Resetting client's password
    	service.resetPass(id);
    	
    	model.addAttribute("message","Password was reset to the default one...");
        
        return "clientsRedirect";
    }

    @RequestMapping(path="/changePass", method=RequestMethod.POST)
    public String changePass(ClientEntity client) throws RecordNotFoundException 
    {
    	service.changePass(client);  
    	
    	return "redirect:/pbb/clients/list";
    }
    
    @RequestMapping(path={"/upload/{id}"})
    public String uploadById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	String priznakDirFull=null;
    	    	
        ClientEntity entity=service.getClientById(id.get());
        
        //Querying if the folder has reach the max number of pictures
        priznakDirFull=fileService.filesCounter(id.get());
        
        model.addAttribute("clients",entity);
        model.addAttribute("priznakDirFull",priznakDirFull);
        
        //System.out.println("Priznak dir full is "+ priznakDirFull);
        
        return "clientsUpload";
    }
    
    @PostMapping("/uploadFile")
    public String uploadFile(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") Long id, RedirectAttributes redirectAttributes,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String cname=null;
    	String username=null;
    	
    	if(file.getOriginalFilename().equals(""))
    	{	
    		
    		ClientEntity entity=service.getClientById(id);
	    	
	    	model.addAttribute("clients",entity);
	    	model.addAttribute("id",id);
	    	model.addAttribute("message","You need to select a file to updload. This selection cannot be empty...");
    		
	    	return "clientsRedirect";
    		
    	}	
    	    	
        fileService.uploadFile(file,id);
        redirectAttributes.addFlashAttribute("message","You successfully uploaded "+ file.getOriginalFilename() +"!");

        ClientEntity entity=service.getClientById(id);
        
        cname=entity.getEmail();
        
      //Getting username
        username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin uploading logo pic for client","Client is "+ cname,username);
    	
    	model.addAttribute("clients",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The picture was uploaded successfully...");
        	        
    	return "clientsRedirect";
    }


    @PostMapping("/DeleteLogo")
    public String deleteFile(Model model,@RequestParam Long id,@RequestParam String logo,HttpServletRequest servlet) throws RecordNotFoundException 
    {
    	String cname=null;
    	String username=null;
    	
    	//Deleting the picture from folder and the picture name from the table
    	fileService.deleteLogo(logo,id);
    	
    	ClientEntity entity=service.getClientById(id);
    	
    	cname=entity.getEmail();
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
		
		//Creating log
		logsServices.createLog("Admin deleting logo pic from store","Client is "+ cname,username);
    	
    	
    	model.addAttribute("clients",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The picture was deleted successfully...");
    	
        return "clientsRedirect";
    }
    
    
    
    @RequestMapping("/ExitMainMenu")
    public String exitToMainMenu() 
    {
        return "redirect:/pbb/index";
    }
    
    
    
    // Store App links
    
    @RequestMapping(path={"/ModifyProfile/{id}"})
    public String editStoreProfile(Model model,@PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
        
        ClientEntity entity=service.getClientById(id.get());
        model.addAttribute("store",entity);
       
        //Preparing the list of cities to be displayed in the form as selection option
        List<CityEntity> listCities=serviceCities.getAllCities();
        model.addAttribute("cities",listCities);
        
      //Preparing the list of states to be displayed in the form as selection option
        List<StateEntity> listStates=serviceStates.getAllStates();
        model.addAttribute("states",listStates);
      
      //Preparing the list of categories to be displayed in the form as selection option
        List<CategoryEntity> listCategories=serviceCategories.getAllCategories();
        model.addAttribute("categories",listCategories);
        
        return "storeEdit";
    }
    
    //Update store profile
    
    @PostMapping("/UpdateStore")
    public String updateStore(Model model,ClientEntity client,@RequestParam Long id) throws RecordNotFoundException 
    {
    	String message="Not saved..";
    	String priznak="messagesClientsMenu";
    	String daysLeftSubscription=null;
    	String cname=null;
    	    	
    	message=service.updateStore(client,id);
    	
    	ClientEntity store=service.getClientById(id);
    	
    	cname=store.getEmail();
    	    	  	
    	//Retrieving messages for publishing and un-publishing
    	CmsParametersEntity entityParamMessage=serviceParams.findByDescription(priznak);
    	
    	//Retrieving days left in subscription
    	daysLeftSubscription=service.getDaysLeftSubscription(id);
    	
    	//Creating log
    	logsServices.createLog("Client updating Store profile","",cname);
    	    	
    	
    	model.addAttribute("entityMessage",entityParamMessage);
    	model.addAttribute("store",store);
    	model.addAttribute("message",message);
    	model.addAttribute("daysLeftSubscription",daysLeftSubscription);
    	        
        return "clientsMenu";
    }
    
    
    //Logo processing
    
    @RequestMapping(path={"/StoresUpload/{id}"})
    public String uploadStoreLogo(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	String priznakDirFull=null;
    	    	
        ClientEntity entity=service.getClientById(id.get());
        
        //Querying if the folder has reach the max number of pictures
        priznakDirFull=fileService.filesCounter(id.get());
        
        model.addAttribute("store",entity);
        model.addAttribute("priznakDirFull",priznakDirFull);
        
        //System.out.println("Priznak dir full is "+ priznakDirFull);
        
        return "storesUpload";
    }
    
    @PostMapping("/DeleteFile")
    public String deleteLogo(Model model,@RequestParam Long id,@RequestParam String logo) throws RecordNotFoundException 
    {
    	String cname=null;
    	
    	//Deleting the picture from folder and the picture name from the table
    	fileService.deleteLogo(logo,id);
    	
    	ClientEntity entity=service.getClientById(id);
    	
    	cname=entity.getEmail();
    	
    	//Creating log
    	logsServices.createLog("Client deleting logo picture","",cname);
    	
    	model.addAttribute("store",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The picture was deleted successfully...");
    	
        return "storesRedirect";
    }
    
    @PostMapping("/UploadLogo")
    public String uploadLogo(Model model,@RequestParam("file") MultipartFile file,@RequestParam("id") Long id, RedirectAttributes redirectAttributes) throws RecordNotFoundException 
    {
    	String cname=null;
    	

    	if(file.getOriginalFilename().equals(""))
    	{	
    		
    		ClientEntity entity=service.getClientById(id);
	    	
	    	model.addAttribute("store",entity);
	    	model.addAttribute("id",id);
	    	model.addAttribute("message","You need to select a file to updload. This selection cannot be empty...");
    		
	    	return "storesRedirect";
    		
    	}	
    	    	
        fileService.uploadFile(file,id);
        redirectAttributes.addFlashAttribute("message","You successfully uploaded "+ file.getOriginalFilename() +"!");

        ClientEntity entity=service.getClientById(id);
        
        cname=entity.getEmail();
        
      //Creating log
    	logsServices.createLog("Client uploading logo picture","",cname);
    	
    	model.addAttribute("store",entity);
    	model.addAttribute("id",id);
    	model.addAttribute("message","The picture was uploaded successfully...");
        	        
    	return "storesRedirect";
    }

  	//Pictures processing
    
    @RequestMapping(path={"/StoresPicsView/{id}"})
    public String storesPicsView(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException 
    {
    	String priznakDirFull=null;
    	String localCategory=null;
    	    	
        ClientEntity entity=service.getClientById(id.get());
        
        //Querying if the folder has reach the max number of pictures
        priznakDirFull=fileService.picturesCounter(id.get());
        
        //Retrieving products list
        List<ProductEntity> listProducts=serviceProducts.findProductsByStore(id.get());
        
      //Inserting categories description
        for(ProductEntity product : listProducts)
        {
        	localCategory=serviceCategories.getCategoryByNum(product.getCategoryNum());
        	
        	product.setCategory(localCategory);
        }
        
        
        model.addAttribute("products",listProducts);
        model.addAttribute("store",entity);
        model.addAttribute("priznakDirFull",priznakDirFull);
        
        //System.out.println("Priznak images dir full is "+ priznakDirFull);
        
        return "storesPicsView";
    }

    @RequestMapping(path={"/ProductEdit/{productId}/{storeId}"})
    public String productEdit(Model model, @PathVariable("productId") Long productId,@PathVariable("storeId") Long storeId) throws RecordNotFoundException 
    {
    	
    	//System.out.println("The value for Client/Store ID is: "+ storeId);
    	//System.out.println("The value for Product ID is: "+ productId);
    	
        ClientEntity entityStore=service.getClientById(storeId);
        ProductEntity entityProduct=serviceProducts.getProductById(productId);
        
      //Processing categories list
      	List<CategoryEntity> categories=serviceCategories.getAllActives();
        
        model.addAttribute("product",entityProduct);
        model.addAttribute("store",entityStore);
        model.addAttribute("categories",categories);
                
        return "productEdit";
    }
    
    @PostMapping("/UploadProductPic")
    public String uploadProductPic(Model model,@RequestParam("file") MultipartFile file,@RequestParam("productId") Long productId,@RequestParam("storeId") Long storeId, RedirectAttributes redirectAttributes) throws RecordNotFoundException 
    {

    	String cname=null;
    	
    	if(file.getOriginalFilename().equals(""))
    	{	
    		
    		ClientEntity entityStore=service.getClientById(storeId);
    		ProductEntity entityProduct=serviceProducts.getProductById(productId);
	    	
    		model.addAttribute("product",entityProduct);
            model.addAttribute("store",entityStore);
	    	model.addAttribute("message","You need to select a file to updload. This selection cannot be empty...");
    		
	    	return "productStoreRedirect";
    		
    	}	
    	    	
        fileService.uploadProductPic(file,storeId,productId);
        redirectAttributes.addFlashAttribute("message","You successfully uploaded a product picture labeled: "+ file.getOriginalFilename() +"!");

        ClientEntity entityStore=service.getClientById(storeId);
        ProductEntity entityProduct=serviceProducts.getProductById(productId);
        
        cname=entityStore.getEmail();
        
        //Creating log
      	logsServices.createLog("Client uploading product picture","",cname);
                      
        
        model.addAttribute("product",entityProduct);
        model.addAttribute("store",entityStore);
    	model.addAttribute("message","The product picture was uploaded successfully...");
        	        
    	return "productStoreRedirect";
    }

    @PostMapping("/StoreUpdate")
    public String storeUpdate(Model model,@RequestParam("storeId") Long storeId,@RequestParam("productId") Long productId,ProductEntity product) throws RecordNotFoundException 
    {   
    	String cname=null;
    	
    	serviceProducts.updateProduct(productId,product.getProduct(),product.getPrice(),product.getStatus(),product.getCondition(),product.getDelivery(),product.getCategoryNum());
    	
    	model.addAttribute("message","Information was updated successfully...");
    	
    	ClientEntity entityStore=service.getClientById(storeId);
        ProductEntity entityProduct=serviceProducts.getProductById(productId);
        
        cname=entityStore.getEmail();
        
      //Creating log
    	logsServices.createLog("Client updating Store profile","",cname);
    	    	
        
        model.addAttribute("product",entityProduct);
        model.addAttribute("store",entityStore);
    	    	
    	return "productStoreRedirect";
    }
    
//Password change
    
    @RequestMapping(path={"/ChangePass/{id}"})
    public String passChange(Model model, @PathVariable("id") Long id) throws RecordNotFoundException 
    {
    	
    	ClientEntity entityStore=service.getClientById(id);
    	
    	model.addAttribute("store",entityStore);
    	    	
        return "storesChangePassForm";
    }
    
    //Save the password
    
    @PostMapping("/ChangePassword")
    public String changePassword(Model model,@RequestParam("storeId") Long storeId,@RequestParam("psw1") String psw1,@RequestParam("psw2") String psw2) throws RecordNotFoundException 
    {   
    	String message="";
    	String encodedPass=null;
    	String cname=null;
    	    	   	
    	ClientEntity entityStore=service.getClientById(storeId);
    	
    	//Encoding password
    	encodedPass=passwordEncoder.encode(psw1);
    	
    	
    	//Changing password for this store
    	service.changeStorePassword(storeId,encodedPass);
    	
    	//System.out.println("The encoded password is "+ encodedPass);
    	
    	cname=entityStore.getEmail();
    	
    	//Creating log
    	logsServices.createLog("Client changing her/his password","",cname);
    	    	
    	
    	message="Your password was successfully changed...";
        
        model.addAttribute("store",entityStore);
        model.addAttribute("message",message);
    	    	
    	return "changePassRedirect";
    }
    
    @RequestMapping(path={"/Unpublish/{id}"})
	public String unpublishStore(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		String newStatus="Due";	
		String message="Site was successfully downloaded...";
		String cname=null;
		
		ClientEntity entity=service.getClientById(id);
		
		//Changing status
		service.statusChange(id,newStatus);
		
		cname=entity.getEmail();
    	
    	//Creating log
    	logsServices.createLog("Client changing her/his status to un-published","",cname);
    	    	
		
		model.addAttribute("store",entity);
		model.addAttribute("message",message);
				
		return "storesRedirect";
	}
	
	@RequestMapping(path={"/Publish/{id}"})
	public String publishStore(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		String newStatus="Public";
		String message="Site was successfully uploaded...";
		String cname=null;
		
		ClientEntity entity=service.getClientById(id);
		
		//Changing status
		service.statusChange(id,newStatus);
		
		cname=entity.getEmail();
    	
    	//Creating log
    	logsServices.createLog("Client changing her/his status to Public","",cname);
		
		model.addAttribute("store",entity);
		model.addAttribute("message",message);
				
		return "storesRedirect";
	}
	
	//Payments view
	
	 @RequestMapping(path={"/StoresPaymentsView/{id}"})
	 public String storesPayments(Model model, @PathVariable("id") Long id) throws RecordNotFoundException 
	 {
	   	String clientId=null;
		
	   	//Converting Long id to String 
	   	clientId=Long.toString(id);
	   	
	   	//Retrieving client info
	    ClientEntity entity=service.getClientById(id);
	        
	  //Retrieving payments list
	    List<PaymentEntityExp> listPayments=servicePayments.findPayments(clientId);
	        
	 	model.addAttribute("payments",listPayments);
	    model.addAttribute("store",entity);
	    	        
	   //System.out.println("Priznak images dir full is "+ priznakDirFull);
	        
	    return "storesPaymentsList";
	 }

	 @RequestMapping(path={"/PictureUpload/{productId}/{storeId}"})
	 public String pictureUpload(Model model, @PathVariable("productId") Long productId,@PathVariable("storeId") Long storeId) throws RecordNotFoundException 
	 {
	    String cname=null;
	    String productName=null;
	    	
	    ClientEntity entityStore=service.getClientById(storeId);
	    ProductEntity entityProduct=serviceProducts.getProductById(productId);
	    
	    cname=entityStore.getEmail();
	    productName=entityProduct.getProduct();
    	
    	//Creating log
    	logsServices.createLog("Client uploading picture for a product","Product is: "+ productName,cname);
	        
	    model.addAttribute("product",entityProduct);
	    model.addAttribute("store",entityStore);
	        
	    return "picturesUpload";
	 }
    
	 @RequestMapping("/selectClients")
	 public String selectClients(Model model)
	 {
		 
		String[] alphaList= {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
		
		model.addAttribute("alphaList",alphaList);
		
		return "clientsSelect";
	 }	
     
	 @RequestMapping("/listAlpha/{alpha}")
	    public String getClientsAlphabetically(Model model,@PathVariable("alpha") String alpha) 
	    {
	        List<ClientEntity> list=service.getClientsAlpha(alpha);
	        
	        int total=0;
	        
	        String daysLeftSubscription=null;
	        //Long clientId=2059L;
	        
	        //Retrieving days left with latest subscription for each client
	        for(ClientEntity client : list)
	        {
	        	 daysLeftSubscription=service.getDaysLeftSubscription(client.getClientid());
	        	 client.setBuffer1(daysLeftSubscription);
	       
	        }
	        
	        //Counting items
	      	total=list.size();
	        
	      	model.addAttribute("stringSearch",alpha);
	      	model.addAttribute("total",total);
	        model.addAttribute("clients",list);
	        return "clientsList";
	        
	    }    
	 
}
