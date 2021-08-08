package com.trc.repositories;

import java.util.List;

import com.trc.entities.CityEntity;

public interface CityRepositoryCustom 
{
	List<CityEntity> getCitiesByState(String state);
}
