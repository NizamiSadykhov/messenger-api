package com.nizamisadykhov.messenger.api.repositories

import com.nizamisadykhov.messenger.api.models.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {
	fun findByConversationId(conversationId: Long): List<Message>
}