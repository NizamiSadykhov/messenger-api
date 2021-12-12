package com.nizamisadykhov.messenger.api.controllers

import com.nizamisadykhov.messenger.api.components.MessageAssembler
import com.nizamisadykhov.messenger.api.helpers.`object`.MessageVO
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import com.nizamisadykhov.messenger.api.services.MessageServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/messages")
class MessageController(
	val messagesService: MessageServiceImpl,
	val userRepository: UserRepository,
	val messageAssembler: MessageAssembler
) {

	@PostMapping
	fun create(@RequestBody messageDetails: MessageRequest, request: HttpServletRequest): ResponseEntity<MessageVO> {
		val principal = request.userPrincipal
		val sender = userRepository.findByUsername(principal.name) as User
		val message = messagesService.sendMessage(
			sender = sender,
			recipientId = messageDetails.recipientId,
			messageText = messageDetails.message
		)
		return ResponseEntity.ok(messageAssembler.toMessageVO(message))
	}

	data class MessageRequest(val recipientId: Long, val message: String)
}