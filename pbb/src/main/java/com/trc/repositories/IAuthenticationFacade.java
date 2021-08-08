package com.trc.repositories;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade 
{
	Authentication getAuthentication();
}

