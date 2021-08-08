package com.trc.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.CategoryEntity;
import com.trc.repositories.CategoryRepository;
import com.trc.repositories.LogsRepository;
import com.trc.repositories.MainRepository;
import com.trc.repositories.ProductRepository;


@Service
public class CategoryServices 
{
	@Autowired
	CategoryRepository repository;
	
	@Autowired
	MainRepository repositoryMain;
	
	@Autowired
	LogsRepository repositoryLogs;
	
	@Autowired
	ProductRepository repositoryProducts;
     
    public List<CategoryEntity> getAllCategories()
    {
        List<CategoryEntity> result=(List<CategoryEntity>) repository.getListByName();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<CategoryEntity>();
        }
    }
    
    public List<CategoryEntity> getAllCategoriesByNum()
    {
        List<CategoryEntity> result=(List<CategoryEntity>) repository.getListByNum();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<CategoryEntity>();
        }
    }
    
    public List<CategoryEntity> getAllActives()
    {
        List<CategoryEntity> result=(List<CategoryEntity>) repository.findAllActive();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<CategoryEntity>();
        }
    }
    
     
    public CategoryEntity getCategoryById(Long id) throws RecordNotFoundException 
    {
        Optional<CategoryEntity> category=repository.findById(id);
         
        if(category.isPresent()) {
            return category.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
     
    public CategoryEntity createOrUpdateCategory(CategoryEntity entity) 
    {
        if(entity.getCategoryId()==null) 
        {
            entity=repository.save(entity);
            
          //Converting long to string
        	String clientIdString=String.valueOf(entity.getCategoryId());
        	
        	//Creating the log
        	repositoryLogs.generateLog("New Category Creation","Admin creating new category "+ entity.getCategoryName(),clientIdString);
             
            return entity;
        } 
        else
        {
            Optional<CategoryEntity> category=repository.findById(entity.getCategoryId());
             
            if(category.isPresent()) 
            {
                CategoryEntity newEntity=category.get();
                
                newEntity.setCategoryNum(entity.getCategoryNum());
                newEntity.setCategoryName(entity.getCategoryName());
                newEntity.setIcon(entity.getIcon());
                newEntity.setActive(entity.getActive());
                newEntity.setPopularity(entity.getPopularity());
                newEntity.setIdentityMetaData(entity.getIdentityMetaData());
                newEntity.setDirectRelationsMetaData(entity.getDirectRelationsMetaData());
                newEntity.setIndirectRelationsMetaData(entity.getIndirectRelationsMetaData());
                
 
                newEntity=repository.save(newEntity);
                
              //Converting long to string
            	String clientIdString=String.valueOf(entity.getCategoryId());
            	
            	//Creating the log
            	repositoryLogs.generateLog("Category Info Update","Admin updating category information "+ entity.getCategoryName(),clientIdString);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deleteCategoryById(Long id) throws RecordNotFoundException 
    {
        Optional<CategoryEntity> category=repository.findById(id);
         
        if(category.isPresent()) 
        {
            repository.deleteById(id);
            
          //Converting long to string
        	String clientIdString=String.valueOf(id);
        	
        	//Creating the log
        	repositoryLogs.generateLog("Category Deletion","Admin deleting category information",clientIdString);
             
            
            
        } else {
            throw new RecordNotFoundException("No category record exist for given id");
        }
    } 
     
    public String getCategoryByNum(String categoryNum) throws RecordNotFoundException 
    {
        String category=repository.getCategoryByNum(categoryNum);
        
        return category;
        
    }
    
    public String createNextCategoryNum()
    {
    	List<String> listCategoriesString=new ArrayList<String>();
    	List<Integer> listCategories=new ArrayList<Integer>();
    	
    	int biggestNumber=0;
    	int bufferInt=0;
    	
    	String nextCategoryNum=null;
    	
    	//Retrieving the list of strings of already existent category numbers
    	
    	listCategoriesString=repository.getCategoriesNumList();
    	
    	//Converting string list to integer list
    	
    	for(String categoryNum : listCategoriesString)
    	{
    		bufferInt=Integer.parseInt(categoryNum); 
    		listCategories.add(bufferInt);
    	}
    	
    	biggestNumber=Collections.max(listCategories);
    	
    	//Adding to next new category
    	biggestNumber++;
    	
    	nextCategoryNum=Integer.toString(biggestNumber);
    	
    	return nextCategoryNum;
    }
    
    
    public List<CategoryEntity> getAllUsedCategories()
    {
        List<CategoryEntity> result=(List<CategoryEntity>) repository.getListByName();
        List<CategoryEntity> listUsedCategories=new ArrayList<CategoryEntity>();
        
        List<String> emails=new ArrayList<String>();  
        
                
        for(CategoryEntity category : result)
        {
        	
        	emails=repositoryProducts.getUsedCat(category.getCategoryNum());
        	
        	if(emails.size()>0)
        		listUsedCategories.add(category);
        }
       
            
        return listUsedCategories;
       
    }
    
    public void updateIdentityMetaData(Long categoryId,String identityMetaData) 
    {
        repository.updateIdentity(identityMetaData,categoryId);
        
        
    }
    
}
