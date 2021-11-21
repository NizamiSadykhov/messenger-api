package com.nizamisadykhov.messenger.api.filters

import com.nizamisadykhov.messenger.api.services.TokenAuthenticationService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

class JWTAuthenticationFilter : GenericFilterBean() {

	/**
	 * Вызывается контейнером всякий раз, когда пара запрос/ответ проходит
	 * через ряд фильтров в результате клиентского запроса на ресурс.
	 * @param filterChain перешедший в doFilter(), позволяет фильтру передавать
	 * запрос и ответ слудщей сущности из ряда фильтров
	 */
	@Throws(IOException::class, ServletException::class)
	override fun doFilter(
		request: ServletRequest,
		response: ServletResponse,
		filterChain: FilterChain
	) {
		val authentication = TokenAuthenticationService.getAuthentication(request as HttpServletRequest)
		SecurityContextHolder.getContext().authentication = authentication
		filterChain.doFilter(request, response)
	}
}