package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.models.Conversation
import com.nizamisadykhov.messenger.api.models.User

interface ConversationService {
	fun createConversation(userA: User, userB: User): Conversation
	fun conversationExists(userA: User, userB: User): Boolean
	fun getConversation(userA: User, userB: User): Conversation?
	fun retrieveThread(conversationId: Long): Conversation
	fun listUserConversation(userId: Long): List<Conversation>
	fun nameSecondParty(conversation: Conversation, userId: Long): String
}