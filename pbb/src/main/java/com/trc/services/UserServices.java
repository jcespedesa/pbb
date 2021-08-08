package com.trc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trc.config.RecordNotFoundException;
import com.trc.entities.UserEntity;
import com.trc.repositories.LogsRepository;
import com.trc.repositories.UserRepository;


@Service
public class UserServices 
{
	@Autowired
	UserRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	LogsRepository repositoryLogs;
	
	@Autowired
	HttpServletRequest servlet;
	
	     
    public List<UserEntity> getAllUsers()
    {
        List<UserEntity> result=(List<UserEntity>) repository.findAll();
         
        if(result.size() > 0) 
        {
            return result;
        } 
        else 
        {
            return new ArrayList<UserEntity>();
        }
    }
     
    public UserEntity getUserById(Long id) throws RecordNotFoundException 
    {
        Optional<UserEntity> user=repository.findById(id);
         
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }
    
    public UserEntity getUserByUsername(String username) throws RecordNotFoundException 
    {
        UserEntity user=repository.findByUsername(username);
         
        return user;
    }
    
     
    public UserEntity createOrUpdateUser(UserEntity entity) 
    {
    	String encodedPass=null;
    	
    	long idLong=0;
    	
        if(entity.getUserid()==null) 
        {
        	//Encoding the default password
            encodedPass=passwordEncoder.encode("pbbPassw0rd");
            
            entity=repository.save(entity);
            
          //Retrieving the client ID
            idLong=entity.getUserid();
            
          //Setting up the default password for the new client
            repository.setDefaultPass(idLong,encodedPass);
             
            return entity;
        } 
        else
        {
            Optional<UserEntity> user=repository.findById(entity.getUserid());
             
            if(user.isPresent()) 
            {
                UserEntity newEntity=user.get();
                
                newEntity.setUsername(entity.getUsername());
                newEntity.setPass(entity.getPass());
                newEntity.setDom(entity.getDom());
                newEntity.setType(entity.getType());
                newEntity.setPriznak(entity.getPriznak());
                newEntity.setActive(entity.getActive());
                newEntity.setEmail(entity.getEmail());
 
                newEntity=repository.save(newEntity);
                 
                return newEntity;
            } 
            else 
            {
                entity=repository.save(entity);
                 
                return entity;
            }
        }
    } 
     
    public void deleteUserById(Long id) throws RecordNotFoundException 
    {
        Optional<UserEntity> user=repository.findById(id);
         
        if(user.isPresent()) 
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id");
        }
    } 
    
    
    public void changePass(long id,String pass) throws RecordNotFoundException 
    {
    	Optional<UserEntity> user=repository.findById(id);
        
        user.ifPresent((UserEntity userResult) -> {
        	
        	userResult.setPass(pass);
        	repository.save(userResult);
        	
        });
    	
    	       
    } 
    
    public void resetPass(Long id) 
    {
    	String encodedPass=null;
    	String username=null;
    	
    	//Trying to set client's password to the default one
    	
    	//Encoding the default password
        encodedPass=passwordEncoder.encode("pbbPassw0rd");
                 	
    	repository.setDefaultPass(id,encodedPass);
    	
    	//Converting Long to String
    	String clientIdString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
    	
    	//Creating the log
    	repositoryLogs.generateLog("Reseting User Password",clientIdString,username);
    	
    	
    }
    
    public void setPass(Long id,String newPass) 
    {
    	    	
    	String encodedPass=null;
    	String username=null;
    	
    	//Trying to set user's new password
    	
    	//Encoding the default password
        encodedPass=passwordEncoder.encode(newPass);
                 	
    	repository.setDefaultPass(id,encodedPass);
    	
    	//Converting Long to String
    	String clientIdString=String.valueOf(id);
    	
    	//Getting username
    	username=(String) servlet.getSession().getAttribute("name");
    	
    	//Creating the log
    	repositoryLogs.generateLog("Setting New Password",clientIdString,username);
    	
    	
    }
    
    public UserEntity getPassByUsername(String username) throws RecordNotFoundException 
    {
        UserEntity user=repository.findByUsername(username);
         
        return user;
    }

}
