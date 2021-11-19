package com.nizamisadykhov.messenger.api.repositories

import com.nizamisadykhov.messenger.api.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
	fun findUsername(username: String): User?
	fun findByPhoneNumber(phoneNumber: String): User?
}