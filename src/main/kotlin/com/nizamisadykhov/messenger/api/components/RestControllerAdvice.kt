package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.constants.ErrorResponse
import com.nizamisadykhov.messenger.api.constants.ResponseConstants
import com.nizamisadykhov.messenger.api.exceptions.UserDeactivatedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/** Обработка возникающих в приложении Spring ошибок */
@ControllerAdvice
class RestControllerAdvice {

	@ExceptionHandler(UserDeactivatedException::class)
	fun userDeactivated(userDeactivatedException: UserDeactivatedException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode =  ResponseConstants.ACCOUNT_DEACTIVATED.value,
			errorMessage = userDeactivatedException.message
		)
		// Возврат ответа ошибки HTTP 403 неудачной авторизации
		return ResponseEntity(res, HttpStatus.UNAUTHORIZED)
	}
}