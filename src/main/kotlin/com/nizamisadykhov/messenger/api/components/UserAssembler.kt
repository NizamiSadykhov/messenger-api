package com.nizamisadykhov.messenger.api.components

import com.nizamisadykhov.messenger.api.helpers.`object`.UserListVO
import com.nizamisadykhov.messenger.api.helpers.`object`.UserVO
import com.nizamisadykhov.messenger.api.models.User
import org.springframework.stereotype.Component

@Component
class UserAssembler {

	fun toUserVO(user: User):  UserVO {
		return UserVO(
			id = user.id,
			username = user.username,
			phoneNumber = user.phoneNumber,
			status = user.status,
			createdAt = user.createdAt.toString()
		)
	}

	fun toUserListVO(users: List<User>): UserListVO {
		val userVOList = users.map { toUserVO(it) }
		return UserListVO(userVOList)
	}
}