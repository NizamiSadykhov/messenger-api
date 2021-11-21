package com.nizamisadykhov.messenger.api.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nizamisadykhov.messenger.api.security.AccountCredentials
import com.nizamisadykhov.messenger.api.services.TokenAuthenticationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class JWTLoginFilter(
	url: String,
	authManager: AuthenticationManager
) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

	init {
		authenticationManager = authManager
	}

	/** Процесс аутентификации */
	@Throws(AuthenticationException::class, IOException::class, ServletException::class)
	override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
		val credentials = ObjectMapper().readValue(req.inputStream, AccountCredentials::class.java)

		return authenticationManager.authenticate(
			UsernamePasswordAuthenticationToken(
				credentials.username,
				credentials.password,
				emptyList()
			)
		)
	}

	/** Успешная аутентификация */
	@Throws(IOException::class, ServletException::class)
	override fun successfulAuthentication(
		request: HttpServletRequest,
		response: HttpServletResponse,
		chain: FilterChain,
		authResult: Authentication
	) {
		TokenAuthenticationService.addAuthentication(response, authResult.name)
	}
}