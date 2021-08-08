package com.trc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter 
{
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring().antMatchers("/pbb/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.inMemoryAuthentication()
	    .withUser("root").password(passwordEncoder.encode("Lenz2142*")).roles("ADMIN");
	}
 

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.antMatcher("/pbb/cms/**")
			.authorizeRequests().anyRequest().authenticated()
			.and().formLogin().loginPage("/pbb/cms/cmsLogin")
			.defaultSuccessUrl("/pbb/cms/CmsMenu", true)
			.failureUrl("/pbb/cms/redirect")
			.permitAll()
			.and().logout().logoutUrl("/pbb/cms/logout").logoutSuccessUrl("/pbb/cms/cmsLogin")
			.and().exceptionHandling().accessDeniedPage("/pbb/cms/accessdenied");
		
		
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.invalidSessionUrl("/pbb/cms/cmsLogin");
					
		http.csrf().disable();
		
	}

	

}
