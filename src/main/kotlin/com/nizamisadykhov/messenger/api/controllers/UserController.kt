package com.nizamisadykhov.messenger.api.controllers

import com.nizamisadykhov.messenger.api.components.UserAssembler
import com.nizamisadykhov.messenger.api.helpers.`object`.UserListVO
import com.nizamisadykhov.messenger.api.helpers.`object`.UserVO
import com.nizamisadykhov.messenger.api.models.User
import com.nizamisadykhov.messenger.api.repositories.UserRepository
import com.nizamisadykhov.messenger.api.services.UserServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UserController(
	val userService: UserServiceImpl,
	val userAssembler: UserAssembler,
	val userRepository: UserRepository
) {

	@PostMapping
	@RequestMapping("/registrations")
	fun create(@Validated @RequestBody userDetails: User): ResponseEntity<UserVO> {
		val user = userService.attemptRegistration(userDetails)
		return ResponseEntity.ok(userAssembler.toUserVO(user))
	}

	@GetMapping
	@RequestMapping("/{user_id}")
	fun show(@PathVariable("user_id") userID: Long): ResponseEntity<UserVO> {
		val user = userService.retrieveUserData(userID)
		return ResponseEntity.ok(userAssembler.toUserVO(user))
	}

	@GetMapping
	@RequestMapping("/details")
	fun echoDetails(request: HttpServletRequest): ResponseEntity<UserVO> {
		val user = userRepository.findByUsername(request.userPrincipal.name) as User
		return ResponseEntity.ok(userAssembler.toUserVO(user))
	}

	@GetMapping
	fun index(request: HttpServletRequest): ResponseEntity<UserListVO> {
		val user = userRepository.findByUsername(request.userPrincipal.name) as User
		val users = userService.listUsers(user)
		return ResponseEntity.ok(userAssembler.toUserListVO(users))
	}

	@PutMapping
	fun update(@RequestBody updateDetails: User, request: HttpServletRequest): ResponseEntity<UserVO> {
		val currentUser = userRepository.findByUsername(request.userPrincipal.name)
		userService.updateUserStatus(currentUser as User, updateDetails)
		return ResponseEntity.ok(userAssembler.toUserVO(currentUser))
	}
}