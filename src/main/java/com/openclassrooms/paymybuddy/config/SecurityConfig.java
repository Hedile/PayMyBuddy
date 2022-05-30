package com.openclassrooms.paymybuddy.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	    private DataSource dataSource;
	
	  @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.jdbcAuthentication().dataSource(dataSource)
	       
	                .usersByUsernameQuery("select email, password, true from user where email = ?;") //SQL query
	                .authoritiesByUsernameQuery("select email, password from user where email = ?;"); //SQL query
	    }
	  
	  @Override
	 protected void configure(HttpSecurity http) throws Exception {
	        http.
	        authorizeRequests()
	    .antMatchers("/register","/homePage","/user/register").permitAll()
         
	                .antMatchers("/css/**","/img/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .formLogin()
	                .loginPage("/login") .permitAll()
	                .defaultSuccessUrl("/homePage")
	                .failureUrl("/login?failure=true")
	                .usernameParameter("email")
	                .permitAll()
	                .and().rememberMe();
	    }
	 @Bean
		public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
		
		}
}
