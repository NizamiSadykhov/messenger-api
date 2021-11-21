package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.constants.ErrorResponse
import com.nizamisadykhov.messenger.api.exceptions.ConversationIdInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/** Обработка возникающих в приложении Spring ошибок */
@ControllerAdvice
class ConversationControllerAdvice {

	@ExceptionHandler
	fun conversationInInvalidException(conversationIdInvalidException: ConversationIdInvalidException): ResponseEntity<ErrorResponse> {
		val res = ErrorResponse("", conversationIdInvalidException.message)
		return ResponseEntity.unprocessableEntity().body(res)
	}
}