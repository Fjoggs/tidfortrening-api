package com.tidfortrening.api.user

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody exercise: UserJsonRequest): Int {
        return userService.createUser(exercise)
    }

    @GetMapping("/read/{id}")
    fun readExercise(@PathVariable id: Int): UserJsonRequest? = userService.readUser(id)

    @PostMapping("/update/{id}")
    fun updateExercise(@PathVariable id: Int, @RequestBody newExercise: UserJsonRequest): UserJsonRequest? =
            userService.updateUser(id, newExercise)

    @PostMapping("/delete/{id}")
    fun deleteExercise(@PathVariable id: Int): Boolean = userService.deleteUser(id)

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"

    data class UserJsonRequest(
            val name: String,
            val gender: String,
            val height: Int,
            val weight: Double,
            val age: Int,
            val maxHeartRate: Int
    )
}