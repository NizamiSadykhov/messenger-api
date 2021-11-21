package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.constants.ErrorResponse
import com.nizamisadykhov.messenger.api.constants.ResponseConstants
import com.nizamisadykhov.messenger.api.exceptions.InvalidUserIdException
import com.nizamisadykhov.messenger.api.exceptions.UserStatusEmptyException
import com.nizamisadykhov.messenger.api.exceptions.UsernameUnavailableException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/** Обработка возникающих в приложении Spring ошибок */
@ControllerAdvice
class UserControllerAdvice {

	@ExceptionHandler(UsernameUnavailableException::class)
	fun usernameUnavailable(usernameUnavailableException: UsernameUnavailableException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode = ResponseConstants.USERNAME_UNAVAILABLE.value,
			errorMessage = usernameUnavailableException.message
		)

		return ResponseEntity.unprocessableEntity().body(res)
	}

	@ExceptionHandler(InvalidUserIdException::class)
	fun invalidId(invalidUserIdException: InvalidUserIdException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode = ResponseConstants.INVALID_USER_ID.value,
			errorMessage = invalidUserIdException.message
		)

		return ResponseEntity.badRequest().body(res)
	}

	@ExceptionHandler(UserStatusEmptyException::class)
	fun statusEmpty(userStatusEmptyException: UserStatusEmptyException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode = ResponseConstants.EMPTY_STATUS.value,
			errorMessage = userStatusEmptyException.message
		)

		return ResponseEntity.unprocessableEntity().body(res)
	}
}