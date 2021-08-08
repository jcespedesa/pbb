package com.trc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.CategoryEntity;
import com.trc.entities.CityEntity;
import com.trc.entities.ClientEntity;
import com.trc.entities.CmsParametersEntity;
import com.trc.entities.MainEntity;
import com.trc.entities.MessagesEntity;
import com.trc.entities.ProductEntity;
import com.trc.entities.RequestsEntity;
import com.trc.entities.StateEntity;
import com.trc.entities.UserEntity;
import com.trc.services.CategoryServices;
import com.trc.services.CityServices;
import com.trc.services.ClientServices;
import com.trc.services.CmsParametersServices;
import com.trc.services.EmailService;
import com.trc.services.LogsServices;
import com.trc.services.MainService;
import com.trc.services.ProductServices;
import com.trc.services.RequestsServices;
import com.trc.services.StateServices;
import com.trc.services.UserServices;


@Controller
@RequestMapping("/pbb")
public class MainController 
{
	@Autowired
    CmsParametersServices serviceParams;
	
	@Autowired
    CategoryServices serviceCategories;
	
	@Autowired
    ClientServices serviceStores;
	
	@Autowired
    ProductServices serviceProducts;
	
	@Autowired
    RequestsServices serviceRequests;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
    StateServices serviceStates;
	
	@Autowired
    CityServices serviceCities;
	
	@Autowired
    MainService service;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    LogsServices logsServices;
			
	@Autowired
    UserServices serviceUser;
	
		
	@RequestMapping("/index")
	public String home(Model model,String priznak)
	{
				
		//Processing messages in the page
		priznak="mainPageMessages";
		List<CmsParametersEntity> entityParams=serviceParams.findByPriznak(priznak);
		
		//Processing stores list in the page
		List<ClientEntity> entityStores=serviceStores.getAllActiveClients();
		
		//Retrieving categories list
		//List<CategoryEntity> entityCategories=serviceCategories.getAllActives();
		List<CategoryEntity> entityCategories=serviceCategories.getAllUsedCategories();
		
		//Processing states list
		List<StateEntity> entityStates=serviceStates.getAllStates();
		
		//Processing messages in the page
		priznak="labelsMainPage";
		List<CmsParametersEntity> entityLabels=serviceParams.findByPriznak(priznak);
		
		//Getting footer information
		priznak="mainPageMessages";
		MainEntity footerParams=service.getByPriznak(priznak);
		
		//Creating log
		logsServices.createLog("Guest visiting","Guest visiting main page","N/A");
		
		model.addAttribute("footerParams",footerParams);
		model.addAttribute("parameters",entityParams);
		model.addAttribute("stores",entityStores);
		model.addAttribute("categories",entityCategories);
		model.addAttribute("labels",entityLabels);
		model.addAttribute("states",entityStates);
						
		return "index";
		
	}
	
	@RequestMapping("/MainMenu")
	public String adminMenu(Model model,HttpServletRequest servlet)
	{
		String username=(String) servlet.getSession().getAttribute("name");
		
		String message=null;
		
  		//System.out.println("Username was: "+ username);
        
  		if(username==null)
  		{	
  			message="Session expired, please login again...";
  			
  			model.addAttribute("message",message);
  			
  			return "cmsLogin";
  			
  		}	
  			
  		else
  		{	
		
  			model.addAttribute("username",username);
		
  			return "mainMenu";
  		}	
	}

	
	@RequestMapping("/Register")
	public String register(Model model)
	{
		//Processing messages in the page
		String priznak="membershipPage";
		String priznak2="mainPageMessages";
		
		List<CmsParametersEntity> entity=serviceParams.findByPriznak(priznak);
		
		String message="Please choose a valid email to begin the registration process...";
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest trying to Register/Login","Guest trying to Register or Login","N/A");
		
		model.addAttribute("footerParams",footerParams);
		model.addAttribute("message",message);
		model.addAttribute("parameters",entity);
		
		return "registerForm";
	}
	
	@RequestMapping("/Login")
	public String login(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest trying to Login","Guest trying to Login","N/A");
				
		model.addAttribute("footerParams",footerParams);
		
		return "loginForm";
	}
	
		
	@RequestMapping(path={"/Details/{id}"})
	public String detailsStore(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException
	{
		String priznak2="mainPageMessages";
		String providerId=null;
		
		//Retrieving client information
		ClientEntity entity=serviceStores.getClientById(id.get());
		
		//Converting id to string
		providerId=String.valueOf(id.get());
		
		//Trying to retrieve the list of products for selected store
		List<ProductEntity> entityProducts=serviceProducts.findProductsByStore(id.get());
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest revising provider","Guest visiting provider page details",providerId);
										
		model.addAttribute("footerParams",footerParams);
		
        model.addAttribute("store",entity);
        model.addAttribute("products",entityProducts);
				
		return "storeDetails";
	}
	
	@RequestMapping("/Product")
	public String detailsProduct(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
								
		model.addAttribute("footerParams",footerParams);
		
		return "productDetails";
	}
	
	@RequestMapping("/Categories")
	public String categories(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Processing stores list in the page
		List<ClientEntity> entityStores=serviceStores.getAllActiveClients();
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
						
		model.addAttribute("footerParams",footerParams);
		
		model.addAttribute("stores",entityStores);
		
		return "categories";
	}
	
	@RequestMapping("/Stores")
	public String stores()
	{
		return "stores";
	}
	
	@RequestMapping("/Promotions")
	public String promotions()
	{
		return "promotions";
	}
	
	@RequestMapping("/Contact")
	public String contactUs(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Preparing messages
		MessagesEntity message=new MessagesEntity();
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest visiting Contact page","Guest visiting contact page details","N/A");
						
		model.addAttribute("footerParams",footerParams);
		
		model.addAttribute("message",message);
		
		return "contactUs";
	}
	
	@RequestMapping("/Privacy")
	public String privacy(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Processing messages in the page
		String priznak="privacyPolicyPage";
		List<CmsParametersEntity> entity=serviceParams.findByPriznak(priznak);
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		model.addAttribute("parameters",entity);
		model.addAttribute("footerParams",footerParams);
		
		//Creating log
		logsServices.createLog("Guest visiting Privacy page","Guest visiting privacy page details","N/A");
		
		return "privacyView";
	}
	
	@RequestMapping("/Terms")
	public String terms(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Processing messages in the page
		String priznak="termsOfUsePage";
		List<CmsParametersEntity> entity=serviceParams.findByPriznak(priznak);
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest visiting Terms page","Guest visiting terms page details","N/A");
		
		model.addAttribute("parameters",entity);
		model.addAttribute("footerParams",footerParams);
		
		return "termsOfUse";
	}
		
		
	@RequestMapping("/Admin")
	public String adminLogin(Model model)
	{
		String priznak2="mainPageMessages";
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
					
		model.addAttribute("footerParams",footerParams);
				
		return "adminLoginForm";
	}
	
	
	//Validating client's login
	
	@PostMapping("/ClientLogin")
	public String clientLogin(Model model,@RequestParam("email") String email,@RequestParam("pass") String pass,HttpServletResponse response)
	{
		
		String storedPass="";
		String message="";
		Boolean loginSuccess=false;
		String newStatus=null;	
							
		storedPass=serviceStores.getPassByEmail(email);
				
		
		if(storedPass==null)
		{
			message="Username or email not found...";
		}
		else
		{	
			//checking if the input password matches with the stored password
	    	boolean isPasswordMatch=passwordEncoder.matches(pass,storedPass);
			
			if(isPasswordMatch)
			{
				
				loginSuccess=true;
				message="Welcome!";
				newStatus="Due";
				
				//Retrieving client identity
				
				ClientEntity entity=serviceStores.getClientByEmail(email);
				
				if(entity.getStatus().equals("New"))
				{
					//This is a newly created user login in for the first time
					//And need to have his/her status changed to "Due" and 
					//All utilities folders need to be created
					
					//Creating utility folders
					serviceStores.createUtilityFolders(email);
					
					//Changing status to "Due"
					serviceStores.statusChange(entity.getClientid(),newStatus);
					
					
				}
				
				
			}	
			else
				message="Wrong password, please try again...";
		}
				
		//Creating log
		logsServices.createLog("Client Login","Client login from main page",email);
		
		//Creating the session cookie
		Cookie sessionCookie=new Cookie("sessionCookie",email);

		//Expire the cookie in twenty minutes (20*60)
		sessionCookie.setMaxAge(1200); 
		
		//Saving the cookie
		response.addCookie(sessionCookie);
		
		//String semail=sessionCookie.getValue();
		//System.out.println("Initial value of session cookie is "+ semail);
					
		model.addAttribute("loginSuccess",loginSuccess);
		model.addAttribute("message",message);
		model.addAttribute("email",email);
		
		return "clieLoginRedirect";
	}
		
	//Redirecting to the client's main menu
	
	@RequestMapping("/ClientsMenu/{email}")
	public String clientsMenu(Model model,@PathVariable("email") String email,HttpServletRequest request)
	{
		int counter=0;
		
		String priznak="messagesClientsMenu";
		String daysLeftSubscription=null;
		String message=null;
				
		Boolean loginSuccess=false;
		
		//Retrieving client identity
		ClientEntity entity=serviceStores.getClientByEmail(email);
		
		//Retrieving messages for publishing and unpublishing
    	CmsParametersEntity entityParamsMessage=serviceParams.findByDescription(priznak);
    	
    	//Retrieving days left in subscription
    	daysLeftSubscription=serviceStores.getDaysLeftSubscription(entity.getClientid());
    	
    	//Trying to retrieve the session cookie
    	Cookie[] cookies=request.getCookies();
    	
    	if(cookies != null) 
    	{
    		 for(Cookie cookie : cookies) 
    		 {
    			 
    			 //System.out.println(cookie.getName());
    			 
    			 if(cookie.getName().equals("sessionCookie")) 
    				 counter++;
   			 
    		 }
    	 }	 
    		 
    	 if(counter==0)
    	 {	
    		        
    	       	//System.out.println("Session expired...");
    		        	
    	       	message="Session expired, please login again...";
    	       	loginSuccess=false;
    		        	
    	       	model.addAttribute("message",message);
    	       	model.addAttribute("email",email);
    	       	model.addAttribute("loginSuccess",loginSuccess);
    		        	
    	       	return "clieLoginRedirect";
    		        	
    	  }   		        
    	
		
		model.addAttribute("store",entity);
		model.addAttribute("entityMessage",entityParamsMessage);
		model.addAttribute("daysLeftSubscription",daysLeftSubscription);
			
		return "clientsMenu";
	}
	
	
	//Search categories by category number
	
	@PostMapping("/Search")
	public String searchCategories(Model model,@RequestParam("categoryNum") String categoryNum) throws RecordNotFoundException
	{
		
		String category=null;
		String priznak2="mainPageMessages";
		
		//System.out.println("The value of categoryNum is "+ categoryNum);
			
		//Getting category description
		category=serviceCategories.getCategoryByNum(categoryNum);
		
		//Getting list of stores having products in selected category
		List<ClientEntity> entity=serviceStores.getStoresByCatNum(categoryNum);
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest using search by category","Guest using search by category dropdown","N/A");
				
		model.addAttribute("footerParams",footerParams);		
		model.addAttribute("stores",entity);
		model.addAttribute("category",category);
		
		return "storesByCategory";
	}
	
	
	//Search categories by city
	
		@PostMapping("/SearchCity")
		public String searchByCity(Model model,@RequestParam("state") String state) throws RecordNotFoundException
		{
						
			String priznak2="mainPageMessages";
								
			List<CityEntity> entity=serviceCities.getCitiesInState(state);
			
			//Getting footer information
			MainEntity footerParams=service.getByPriznak(priznak2);
			
			//Creating log
			logsServices.createLog("Guest using search by city","Guest using search by city dropdown","N/A");
					
			model.addAttribute("footerParams",footerParams);		
			model.addAttribute("cities",entity);
			model.addAttribute("state",state);
			
			//System.out.println(entity);
			
			return "citiesByState";
		}
	
		
		//Search stores by city
		
		@PostMapping("/StoresByCity")
		public String storesByCity(Model model,@RequestParam("city") String city) throws RecordNotFoundException
		{
							
			String priznak2="mainPageMessages";
									
			List<ClientEntity> entity=serviceStores.getStoresByCity(city);
				
			//Getting footer information
			MainEntity footerParams=service.getByPriznak(priznak2);
					
			model.addAttribute("footerParams",footerParams);		
			model.addAttribute("stores",entity);
			model.addAttribute("city",city);
				
			//System.out.println(entity);
				
			return "storesByCity";
		}
	
		//Searching stores by category
		
		@RequestMapping("/categoriesSel")
		public String categorySelection(Model model)
		{
			String priznak2="mainPageMessages";
			
			//Retrieving categories list
			//List<CategoryEntity> entityCategories=serviceCategories.getAllCategories();
			List<CategoryEntity> entityCategories=serviceCategories.getAllUsedCategories();
			
			
			//Getting footer information
			MainEntity footerParams=service.getByPriznak(priznak2);
			
			model.addAttribute("categories",entityCategories);
			model.addAttribute("footerParams",footerParams);
			
			//Creating log
			logsServices.createLog("Guest visiting Privacy page","Guest searching stores by category","N/A");
			
			return "categoriesSelection";
		}
		
		
	
	//Registration process for new user
	
	@PostMapping("/RegisterPre")
	public String clientRegistration(Model model,@RequestParam("email") String email)
	{
		String priznak2="mainPageMessages";
		String message="Unable to connect the login service, please try again later...";
		String encodedPass=null;
		
		Boolean priznakExistentEmail=false;
					
		priznakExistentEmail=serviceStores.getDuplicatedEmail(email);
		
		//System.out.println("The value of priznakExistentEmail is "+ priznakExistentEmail);
		
		if(priznakExistentEmail==true)
		{
			message="This email already existis in our system, please choose another one. If you already have an account, please press on the 'Sign in' link below...";
			
			//Getting footer information
			MainEntity footerParams=service.getByPriznak(priznak2);
			
			//Creating log
			logsServices.createLog("Guest failed to register","Guest failing to register in main page",email);
					
			model.addAttribute("footerParams",footerParams);
			model.addAttribute("message",message);
						
			return "registerForm";
		}
		
		else
		{
			int passwordInt=0;
			
			String password=null;
			
			//Generating a random password of 4 digits
			passwordInt=emailService.generateRandomIntIntRange(1000,9999);
			
			//Converting the password int to password string
			password=Integer.toString(passwordInt);
			
			//Encoding the generated random password
            encodedPass=passwordEncoder.encode(password);
			
			
			//Sending the registration email
			emailService.sendMail(email,"Sending your password for the Peoples Best Buy Website",password);
			
			//Generating the message for the new user
			message="An email with your password was sent to the provided email, please now login in the system using the link 'Sign in'...(Please check the Junk folder as well)";
			
			//Saving username, email and encoded password into table clients, it will create status="New" as well
			serviceStores.createNewUsername(email,encodedPass);
			
			//Creating utility folders for the new user (This doesn't need to be here)
			//It is not here anymore was moved to line 212 in /ClientLogin
			
			
			//Getting footer information
			MainEntity footerParams=service.getByPriznak(priznak2);
			
			//Creating log
			logsServices.createLog("Guest registered successful","Guest registered successful in main page",email);
					
			model.addAttribute("footerParams",footerParams);
			
			model.addAttribute("priznakExistentEmail",priznakExistentEmail);
			model.addAttribute("message",message);
			model.addAttribute("email",email);
			
			return "registerPre";
		}	
			
		
	}
	
	//Purchasing process
	@RequestMapping(path={"/StoresPurchase/{id}"})
	public String purchaseStore(Model model, @PathVariable("id") Long id) throws RecordNotFoundException
	{
		String priznak2="mainPageMessages";
		String storeId=null;
		
		Long storeIdLong=null;
				
		//Getting product information
		ProductEntity entity=serviceProducts.getProductById(id);
		
		storeId=entity.getStoreId();
		storeIdLong=Long.parseLong(storeId);
		
		//Getting store information
		ClientEntity storeEntity=serviceStores.getClientById(storeIdLong);
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
						
		model.addAttribute("footerParams",footerParams);		
		model.addAttribute("product",entity);
		model.addAttribute("request",new RequestsEntity());
		model.addAttribute("store",storeEntity);
        				
		return "storePurchasing";
	}
	
	@RequestMapping("/About")
	public String about(Model model)
	{
		//Processing messages in the page
		String priznak="aboutPage";
		String priznak2="mainPageMessages";
		
		List<CmsParametersEntity> entity=serviceParams.findByPriznak(priznak);
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
				
		model.addAttribute("parameters",entity);
		model.addAttribute("footerParams",footerParams);
		
		return "about";
	}
	
	//Sending purchase request email  
	
	@PostMapping("/CreateRequest")
	public String sendingRequest(Model model,RequestsEntity request,@RequestParam String id,@RequestParam String product)
	{
		Long idLong=Long.parseLong(id);
		
		String priznak2="mainPageMessages";
		
		String to=null;
		String message=null;
		String messageMain=null;
		String messageTrail=null;
		
		String repitaya=" , ";
		String tochka=" . ";
		
		String result="The merchant will contact you very soon to arrange your purchase...";
		
		messageMain=request.getInstructions();
				
		messageTrail="Selected Product was: "+ product + tochka + " My name is: "+ request.getCname() + repitaya;
	    messageTrail=messageTrail +" My email is: "+ request.getEmail() + repitaya + "My phone is: "+ request.getPhone();
		message=messageMain + tochka + messageTrail;
		
		//Creating log
		logsServices.createLog("Guest purchasing product","Guest is "+ request.getCname(),id);
		
		//Trying to retrieve the store email
		to=serviceStores.getEmail(idLong);
		
		//Trying to save the request
		serviceRequests.createOrUpdateRequest(request);			
			
		//Sending the request email
		emailService.sendMail(to,"Sending a request for a product you sell in Peoples Best Buy Website",message);
		
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
				
		model.addAttribute("footerParams",footerParams);
		model.addAttribute("result",result);
						
		return "postSending";
			
	}	
	
	
	@RequestMapping("/EmailFriend")
	public String emailToFriend(Model model)
	{
		String priznak2="mainPageMessages";
		
		
		//Preparing messages
		MessagesEntity message=new MessagesEntity();
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
		
		//Creating log
		logsServices.createLog("Guest trying to send email to a friend",message.getEmail(),message.getCname());
						
		model.addAttribute("footerParams",footerParams);
		
		model.addAttribute("message",message);
		
		return "emailToFriend";
	}
	
	
//Validating user's login
	
  	@PostMapping("/cmsLogin")
  	public String userLogin(Model model,@RequestParam("username") String username,@RequestParam("pass") String pass,HttpSession session) throws RecordNotFoundException
  	{
  		
  		String storedPass=null;
  		String message="";
  		Boolean loginSuccess=false;
  		  							
  		UserEntity entity=serviceUser.getPassByUsername(username);
  		
  		if(entity==null)
  		{
  			message="Username not found";
  		}
  		else
  		{	
  			storedPass=entity.getPass();
  			
  			//checking if the input password matches with the stored password
  	    	boolean isPasswordMatch=passwordEncoder.matches(pass,storedPass);
  			
  			if(isPasswordMatch)
  			{
  				
  				loginSuccess=true;
  				message="Welcome!";
  				
  				//Creating the session
  				session.setAttribute("name",username);
  				
  			//Creating log
  		  		logsServices.createLog("Admin login into the system","",username);
  			 				  				
  			}	
  			else
  			{	
  				message="Wrong password, please try again...";
  		//Creating log
  				logsServices.createLog("Admin failed to login into the system","Wrong username/password",username);
  			}	
  		}
  		
  		model.addAttribute("loginSuccess",loginSuccess);
  		model.addAttribute("message",message);
  		model.addAttribute("username",username);
  		
  		return "userLoginRedirect";
  	}
  		
  	//Redirecting to the cms main menu
  	
  	@RequestMapping("/CmsMenu")
  	public String mainMenu(Model model,HttpServletRequest servlet)
  	{
  		String username=null;
  		String message=null;
  		
  		//Getting username
  		username=(String) servlet.getSession().getAttribute("name");
  		
  		System.out.println("Username was: "+ username);
        
  		if(username==null)
  		{	
  			message="Session expired, please login again...";
  			
  			model.addAttribute("message",message);
  			
  			return "cmsLogin";
  			
  		}	
  			
  		else
  		{	
  			model.addAttribute("username",username);
  		
  			return "mainMenu";
  		}	
  	}
  	
  	@RequestMapping("/cms")
  	public String cms()
  	{
  		  	  		
  		return "cmsLogin";
  	}
  	
  	
  	//Forgot password section
  	
  	@RequestMapping("/ForgotPassForm")
  	public String forgotPassForm(Model model)
  	{
  		String priznak2="mainPageMessages";
		
		//Getting footer information
		MainEntity footerParams=service.getByPriznak(priznak2);
  		
		model.addAttribute("footerParams",footerParams);
  		  	  		
  		return "forgotPassForm";
  	}
  	
  	@PostMapping("/ForgotPass")
	public String forgotPass(Model model,@RequestParam("email") String email,HttpServletResponse response)
	{
		int passwordInt=0;
  		
		Boolean emailExist=false;
		
		String message="";
		String password=null;
		String encodedPass=null;
		
							
		emailExist=serviceStores.emailExist(email);
				
		
		if(emailExist==false)
		{
			message="Email not found...";
			
		}
		else
		{
			//Creating log
			logsServices.createLog("Forgot password subroutine","Guest requesting new password",email);
						
			//Generating a random password of 4 digits
			passwordInt=emailService.generateRandomIntIntRange(1000,9999);
			
			//Converting the password int to password string
			password=Integer.toString(passwordInt);
			
			//Encoding the generated random password
            encodedPass=passwordEncoder.encode(password);
			
			
			//Sending the registration email
			emailService.sendMail(email,"Sending your password for the Peoples Best Buy Website",password);
			
			//Generating the message for the new user
			message="An email with your password was sent to the provided email, please now login in the system...(Please check the Junk folder as well)";
			
			//Saving user name, email and encoded password into table clients
			serviceStores.savingNewPass(email,encodedPass);
			
			
		}
		
		model.addAttribute("message",message);
		model.addAttribute("emailExist",emailExist);
		
		return "forgotPassRedirect";
	}	
  	
  	
}
