package com.trc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.ClientEntity;
import com.trc.repositories.ClientRepository;
import com.trc.repositories.ProductRepository;


@Service
public class ToolsServices 
{

	@Autowired
	ClientRepository repositoryClients;
	
	@Autowired
	ProductRepository repositoryProducts;
	
	public int getAllClieCat()
    {
		int counter=0;
		
		String storeId=null;
				
		//Retrieving the list of stores
        List<ClientEntity> listClients=(List<ClientEntity>) repositoryClients.getListClieCat();
        
        for(ClientEntity client : listClients)
        {
        	storeId=String.valueOf(client.getClientid());
        	
        	//Inserting store found category number in every existent product
        	repositoryProducts.insertCategoryNum(storeId,client.getCategoryNum());
        	
        	counter++;
        	
        }
                       
        return counter;
    }
	
}
