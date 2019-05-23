package com.tidfortrening.api.user

import com.tidfortrening.api.user.UserController.*

class UserService(private val userDao: UserDao) {

    fun createUser(user: UserJsonRequest): Int = userDao.createUser(user)

    fun readUser(id: Int): UserJsonRequest? = userDao.readUser(id)

    fun updateUser(id: Int, newExercise: UserJsonRequest): UserJsonRequest? =
            userDao.updateUser(id, newExercise)

    fun deleteUser(id: Int): Boolean = userDao.deleteUser(id)
}