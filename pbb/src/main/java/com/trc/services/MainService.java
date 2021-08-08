package com.trc.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.entities.MainEntity;
import com.trc.repositories.MainRepository;


@Service
@Transactional
public class MainService 
{
	@Autowired
	MainRepository repository;
	
	public List<MainEntity> findByPriznak(String priznak) 
	{
        return repository.findByPriznak(priznak);
    }
	
	public MainEntity getByPriznak(String priznak) 
	{
        return repository.getByPriznak(priznak);
    }
	
}
