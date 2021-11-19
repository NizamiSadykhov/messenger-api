package com.nizamisadykhov.messenger.api.listeners

import com.nizamisadykhov.messenger.api.models.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class UserListener {
	@PrePersist // вызывать функцию до сохранения записи
	@PreUpdate  // вызывать функцию до обновления записи
	fun hashPassword(user: User) {
		user.password = BCryptPasswordEncoder().encode(user.password)
	}
}