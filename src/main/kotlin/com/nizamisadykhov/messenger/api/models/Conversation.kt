package com.nizamisadykhov.messenger.api.models

import org.springframework.format.annotation.DateTimeFormat
import java.time.Instant
import java.util.*
import javax.persistence.*


@Entity
/**
 * Беседа
 * @param sender отправитель
 * @param recipient получатель
 * @param id  id
 * @param createdAt дата создания
 */
class Conversation(
	@ManyToOne(optional = false)
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
	var sender: User? = null,

	@ManyToOne(optional = false)
	@JoinColumn(name = "recipient_id", referencedColumnName = "id")
	var recipient: User? = null,

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = 0,

	@DateTimeFormat
	val createdAt: Date = Date.from(Instant.now())
) {
	/** Коллеция сообщений */
	@OneToMany(mappedBy = "conversation", targetEntity = Message::class)
	private var messages: Collection<Message>? = null
}
