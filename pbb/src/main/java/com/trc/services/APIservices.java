package com.trc.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class APIservices 
{

	 public String getSynonyms(String fullPath)
	 {
	
		 String unwantedString1="noun";
		 String unwantedString2="syn";
		 String unwantedString3="verb";
		 String unwantedString4="adjective";
		 String unwantedString5="rel";
		 String unwantedString6="hose";
		 String unwantedString7="ant";
		 String unwantedString8="sim";
		 String pustoy="";
		 		
		 RestTemplate restTemplate=new RestTemplate();
	    	
	    String result=restTemplate.getForObject(fullPath,String.class);
	    	    
	    //Removing unnecessary strings
	    
	    result=result.replaceAll(unwantedString1,pustoy);
	    result=result.replaceAll(unwantedString2,pustoy);
	    result=result.replaceAll(unwantedString3,pustoy);
	    result=result.replaceAll(unwantedString4,pustoy);
	    result=result.replaceAll(unwantedString5,pustoy);
	    result=result.replaceAll(unwantedString6,pustoy);
	    result=result.replaceAll(unwantedString7,pustoy);
	    result=result.replaceAll(unwantedString8,pustoy);
	    
	    result=result.replaceAll("[\\r\\n]","");
	    result=result.replaceAll("[||]"," ");
	    result=result.trim();
	    
	    return result;
		 
	 }
	
}
