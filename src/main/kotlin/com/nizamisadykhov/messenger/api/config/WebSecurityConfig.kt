package com.nizamisadykhov.messenger.api.config

import com.nizamisadykhov.messenger.api.filters.JWTAuthenticationFilter
import com.nizamisadykhov.messenger.api.filters.JWTLoginFilter
import com.nizamisadykhov.messenger.api.services.AppUserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/** Пользовательская конфигурация веб-безопасности */
@Configuration
@EnableWebSecurity //  включение поддержки веб-безопасности
class WebSecurityConfig(val userDetailsService: AppUserDetailsService) : WebSecurityConfigurerAdapter() {

	// Определяет какие URL-пути должны быть защищены
	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(
				JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter::class.java
			)
			.addFilterBefore(
				JWTAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter::class.java
			)
	}

	@Throws(Exception::class)
	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService<UserDetailsService>(userDetailsService)
			.passwordEncoder(BCryptPasswordEncoder())
	}

}