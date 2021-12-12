package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.exceptions.MessageEmptyException
import com.nizamisadykhov.messenger.api.exceptions.MessageRecipientInvalidException
import com.nizamisadykhov.messenger.api.models.Conversation
import com.nizamisadykhov.messenger.api.models.Message
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.ConversationRepository
import com.nizamisadykhov.messenger.api.repositories.MessageRepository
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class MessageServiceImpl(
	val repository: MessageRepository,
	val conversationRepository: ConversationRepository,
	val conversationService: ConversationService,
	val userRepository: UserRepository
): MessageService {

	@Throws(MessageEmptyException::class, MessageRecipientInvalidException::class)
	override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {
		val optional = userRepository.findById(recipientId)

		if (optional.isPresent) {
			val recipient = optional.get()

			val conversation: Conversation = if (conversationService.conversationExists(sender, recipient)) {
				conversationService.getConversation(sender, recipient) as Conversation
			} else {
				conversationService.createConversation(sender, recipient)
			}
			conversationRepository.save(conversation)

			val message = Message(sender, recipient, messageText, conversation)
			repository.save(message)
			return message
		} else {
			throw MessageRecipientInvalidException("The recipient id '$recipientId' is invalid.")
		}
		//throw MessageEmptyException()
	}
}