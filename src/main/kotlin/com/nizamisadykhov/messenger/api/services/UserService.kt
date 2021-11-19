package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.models.User

interface UserService {
	/**	Попытка регистрации	*/
	fun attemptRegistration(userDetails: User): User

	fun listUsers(currentUser: User): List<User>

	/** Получить данные пользователя */
	fun retrieveUserData(userName: String): User?

	/** Получить данные пользователя */
	fun retrieveUserData(id: Long): User?

	/** Имя пользователя существует */
	fun usernameExists(username: String): Boolean
}