package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.helpers.`object`.ConversationListVO
import com.nizamisadykhov.messenger.api.helpers.`object`.ConversationVO
import com.nizamisadykhov.messenger.api.helpers.`object`.MessageVO
import com.nizamisadykhov.messenger.api.models.Conversation
import com.nizamisadykhov.messenger.api.services.ConversationServiceImpl
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(
	val conversationService: ConversationServiceImpl,
	val messageAssembler: MessageAssembler
) {
	fun toConversationVO(conversation: Conversation, userId: Long): ConversationVO {
		val conversationMessages: ArrayList<MessageVO> = ArrayList()
		conversation.messages.mapTo(conversationMessages) {
			messageAssembler.toMessageVO(it)
		}
		return ConversationVO(
			conversationId = conversation.id,
			secondPartyUsername = conversationService.nameSecondParty(conversation, userId),
			messages = conversationMessages
		)
	}

	fun toConversationListVO(conversation: List<Conversation>, userId: Long): ConversationListVO {
		val conversationVOList = conversation.map {
			toConversationVO(it, userId)
		}
		return ConversationListVO(conversationVOList)
	}
}