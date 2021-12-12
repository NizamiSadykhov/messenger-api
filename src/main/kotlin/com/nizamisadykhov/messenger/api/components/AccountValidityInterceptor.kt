package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.exceptions.UserDeactivatedException
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccountValidityInterceptor(val userRepository: UserRepository): HandlerInterceptor {

	@Throws(UserDeactivatedException::class)
	override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
		val principal: Principal? = request.userPrincipal

		if (principal != null) {
			val user = userRepository.findByUsername(principal.name) as User
			if (user.accountStatus == "deactivated") {
				throw UserDeactivatedException("The account of this user has been deactivated.")
			}
		}

		return super.preHandle(request, response, handler)
	}
}