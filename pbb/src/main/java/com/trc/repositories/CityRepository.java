package com.trc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trc.entities.CityEntity;


@Repository("cityRepository")
public interface CityRepository extends CrudRepository<CityEntity,Long>, CityRepositoryCustom, JpaRepository<CityEntity,Long> 
{
	CityEntity findByCity(String city);
}
