package com.nizamisadykhov.messenger.api.services

import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UserServiceImpl(val repository: UserRepository) : UserService {

	@Throws(UsernameUnavailableException::class)
	override fun attemptRegistration(userDetails: User): User {
		if (!usernameExists(userDetails.username)) {
			val user = User()
			user.username = userDetails.username
			user.phoneNumber = userDetails.phoneNumber
			user.password = userDetails.password
			repository.save(user)
			obscurePassword(user)
			return user
		}
		throw UsernameUnavailableException("The username ${userDetails.username} is unavailable.")
	}

	@Throws(UserStatusEmptyExceptions::class)
	private fun updateUserStatus(currentUser: User, updateDetails: User): User {
		if (updateDetails.status.isNotEmpty()) {
			currentUser.status = updateDetails.status
			repository.save(currentUser)
			return currentUser
		}
		throw UserStatusEmptyExceptions()
	}

	override fun listUsers(currentUser: User): List<User> {
		return repository.findAll().mapTo(ArrayList()) { it }.filter { it != currentUser }
	}

	override fun retrieveUserData(userName: String): User? {
		val user = repository.findUsername(userName)
		obscurePassword(user)
		return user
	}

	@Throws(InvalidateUserExecptions::class)
	override fun retrieveUserData(id: Long): User? {
		val userOptional = repository.findById(id)
		if (userOptional.isPresent) {
			val user = userOptional.get()
			obscurePassword(user)
			return user
		}
		throw InvalidateUserExecptions("A user with an id of '$id' does not exist.")
	}

	override fun usernameExists(username: String): Boolean {
		return repository.findUsername(username) != null
	}

	private fun obscurePassword(user: User?) {
		user?.password = "XXX XXXX XXX"
	}
}