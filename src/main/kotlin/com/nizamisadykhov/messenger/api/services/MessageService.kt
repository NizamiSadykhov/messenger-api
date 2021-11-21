package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.models.Message
import com.nizamisadykhov.messenger.api.models.User

interface MessageService {
	fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}