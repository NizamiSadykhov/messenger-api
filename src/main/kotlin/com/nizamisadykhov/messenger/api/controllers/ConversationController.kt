package com.nizamisadykhov.messenger.api.controllers

import com.nizamisadykhov.messenger.api.components.ConversationAssembler
import com.nizamisadykhov.messenger.api.helpers.`object`.ConversationListVO
import com.nizamisadykhov.messenger.api.helpers.`object`.ConversationVO
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import com.nizamisadykhov.messenger.api.services.ConversationServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/conversation")
class ConversationController(
	val conversationService: ConversationServiceImpl,
	val conversationAssembler: ConversationAssembler,
	val userRepository: UserRepository
) {

	@GetMapping
	fun list(request: HttpServletRequest): ResponseEntity<ConversationListVO> {
		val user = userRepository.findByUsername(request.userPrincipal.name) as User
		val conversations = conversationService.listUserConversation(user.id)
		return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations, user.id))
	}

	@GetMapping
	@RequestMapping("/{conversation_id}")
	fun show(
		@PathVariable(name = "conversation_id") conversationId: Long,
		request: HttpServletRequest
	): ResponseEntity<ConversationVO> {
		val user = userRepository.findByUsername(request.userPrincipal.name) as User
		val conversationThread = conversationService.retrieveThread(conversationId)
		return ResponseEntity.ok(conversationAssembler.toConversationVO(conversationThread, user.id))
	}
}