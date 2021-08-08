package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.ProductEntity;
import com.trc.repositories.LogsRepository;
import com.trc.repositories.ProductRepository;


@Service
public class ProductServices 
{
	@Autowired
	ProductRepository repository;
	
	@Autowired
    LogsRepository repositoryLogs;
     
    public List<ProductEntity> getAllProducts()
    {
        List<ProductEntity> result=(List<ProductEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<ProductEntity>();
        }
    }
     
    public ProductEntity getProductById(Long id) throws RecordNotFoundException 
    {
        Optional<ProductEntity> product=repository.findById(id);
         
        if(product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }
     
    public ProductEntity createOrUpdateProduct(ProductEntity entity) 
    {
        if(entity.getProductid()==null) 
        {
            entity=repository.save(entity);
            
          //Converting long to string
        	String clientIdString=String.valueOf(entity.getStoreId());
        	
        	//Creating the log
        	repositoryLogs.generateLog("New Product creation","New product is "+ entity.getProduct(),clientIdString);
             
             
            return entity;
        } 
        else
        {
            Optional<ProductEntity> product=repository.findById(entity.getProductid());
             
            if(product.isPresent()) 
            {
                ProductEntity newEntity=product.get();
                
                newEntity.setProduct(entity.getProduct());
                newEntity.setStoreId(entity.getStoreId());
                newEntity.setFileName(entity.getFileName());
                newEntity.setPrice(entity.getPrice());
                newEntity.setStatus(entity.getStatus());
                newEntity.setCondition(entity.getCondition());
                newEntity.setDelivery(entity.getDelivery());
                newEntity.setCategoryNum(entity.getCategoryNum());
                                
                newEntity=repository.save(newEntity);
                
              //Converting long to string
            	String clientIdString=String.valueOf(entity.getStoreId());
            	
            	//Creating the log
            	repositoryLogs.generateLog("Product Update","Product ID is "+ entity.getProduct(),clientIdString);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deleteProductById(Long id) throws RecordNotFoundException 
    {
        Optional<ProductEntity> product=repository.findById(id);
         
        if(product.isPresent()) 
        {
            repository.deleteById(id);
            
          //Converting long to string
        	String clientIdString=String.valueOf(id);
        	
        	//Creating the log
        	repositoryLogs.generateLog("Product Deletion","Product is being deleted",clientIdString);
            
            
        } else 
        {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }
    
    public List<ProductEntity> findProductsByStore(Long id)
    {
    	String idString=null;
    	
    	idString=Long.toString(id);
    	
    	List<ProductEntity> result=(List<ProductEntity>) repository.findProductsByStoreId(idString);
    	
    	return result;
    }

    public void updateProduct(Long productId,String product,String price,String status,String condition,String delivery,String categoryNum) 
    {
    
    	repository.updateProductExp(productId,product,price,status,condition,delivery,categoryNum);
    	
    }
    
}
