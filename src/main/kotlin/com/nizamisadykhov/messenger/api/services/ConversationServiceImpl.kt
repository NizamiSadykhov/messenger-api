package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.exceptions.ConversationIdInvalidException
import com.nizamisadykhov.messenger.api.models.Conversation
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.ConversationRepository

class ConversationServiceImpl(val repository: ConversationRepository) : ConversationService {

	override fun createConversation(userA: User, userB: User): Conversation {
		val conversation = Conversation(userA, userB)
		repository.save(conversation)
		return conversation
	}

	override fun conversationExists(userA: User, userB: User): Boolean {
		if (repository.findBySenderIdAndRecipientId(userA.id, userB.id) != null)
			return true

		return repository.findBySenderIdAndRecipientId(userB.id, userA.id) != null
	}

	override fun getConversation(userA: User, userB: User): Conversation? {
		repository.findBySenderIdAndRecipientId(userA.id, userB.id)?.let {
			return it
		}
		repository.findBySenderIdAndRecipientId(userB.id, userA.id)?.let {
			return it
		}
		return null
	}

	override fun retrieveThread(conversationId: Long): Conversation {
		val conversation = repository.findById(conversationId)
		if (conversation.isPresent) {
			return conversation.get()
		}
		throw ConversationIdInvalidException("Invalid conversion id '$conversationId'")
	}

	override fun listUserConversation(userId: Long): List<Conversation> {
		val conversionList = mutableListOf<Conversation>()
		conversionList.addAll(repository.findBySenderId(userId))
		conversionList.addAll(repository.findByRecipientId(userId))

		return conversionList
	}

	override fun nameSecondParty(conversation: Conversation, userId: Long): String {
		return if (conversation.sender?.id == userId)
			conversation.recipient?.username as String
		else
			conversation.sender?.username as String
	}

}