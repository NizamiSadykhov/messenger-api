package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.constants.ErrorResponse
import com.nizamisadykhov.messenger.api.constants.ResponseConstants
import com.nizamisadykhov.messenger.api.exceptions.MessageEmptyException
import com.nizamisadykhov.messenger.api.exceptions.MessageRecipientInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/** Обработка возникающих в приложении Spring ошибок */
@ControllerAdvice
class MessageControllerAdvice {

	@ExceptionHandler(MessageEmptyException::class)
	fun messageEmpty(messageEmptyException: MessageEmptyException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode = ResponseConstants.MESSAGE_EMPTY.value,
			errorMessage = messageEmptyException.message
		)

		return ResponseEntity.unprocessableEntity().body(res)
	}

	@ExceptionHandler(MessageRecipientInvalidException::class)
	fun messageRecipientInvalid(messageRecipientInvalidException: MessageRecipientInvalidException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse(
			errorCode = ResponseConstants.MESSAGE_RECIPIENT_INVALID.value,
			errorMessage = messageRecipientInvalidException.message
		)

		return ResponseEntity.unprocessableEntity().body(res)
	}
}