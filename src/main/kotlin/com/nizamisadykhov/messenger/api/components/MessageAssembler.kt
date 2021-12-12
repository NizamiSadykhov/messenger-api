package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.helpers.`object`.MessageVO
import com.nizamisadykhov.messenger.api.models.Message
import org.springframework.stereotype.Component

@Component
class MessageAssembler {
	fun toMessageVO(message: Message): MessageVO {
		return MessageVO(
			id = message.id,
			senderId = message.sender?.id,
			recipientId = message.recipient?.id,
			conversationId = message.conversation?.id,
			body = message.body,
			createdAt = message.createdAt.toString()
		)
	}
}