package com.tidfortrening.api.user

import com.tidfortrening.api.user.UserController.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class UserDao(dataSource: DataSource) {

    init {
        Database.connect(dataSource)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)
        }
    }

    fun createUser(exerciseJsonRequest: UserJsonRequest): Int =
            transaction {
                addLogger(StdOutSqlLogger)
                val user = User.new {
                    name = exerciseJsonRequest.name
                    gender = exerciseJsonRequest.gender
                    height = exerciseJsonRequest.height
                    weight = exerciseJsonRequest.weight
                    age = exerciseJsonRequest.age
                    maxHeartRate = exerciseJsonRequest.maxHeartRate
                }
                user.id.value
            }

    fun readUser(id: Int): UserJsonRequest? =
            transaction {
                addLogger(StdOutSqlLogger)
                val user = User.findById(id)
                user?.let {
                    UserJsonRequest(
                            user.name,
                            user.gender,
                            user.height,
                            user.weight,
                            user.age,
                            user.maxHeartRate
                    )
                }
            }

    fun updateUser(id: Int, newUser: UserJsonRequest): UserJsonRequest? =
            transaction {
                addLogger(StdOutSqlLogger)
                val user = User.findById(id)
                user?.let {
                    user.name = newUser.name
                    user.gender = newUser.gender
                    user.height = newUser.height
                    user.weight = newUser.weight
                    user.age = newUser.age
                    user.maxHeartRate = newUser.maxHeartRate
                    UserJsonRequest(
                            user.name,
                            user.gender,
                            user.height,
                            user.weight,
                            user.age,
                            user.maxHeartRate
                    )
                }
            }

    fun deleteUser(id: Int): Boolean =
            transaction {
                addLogger(StdOutSqlLogger)
                val user = User.findById(id)
                user?.let {
                    user.delete()
                    true
                } ?: false
            }
}

object Users : IntIdTable() {
    val name = varchar("name", 250)
    val gender = varchar("gender", 1)
    val height = integer("height")
    val weight = double("weight")
    val age = integer("age")
    val maxHeartRate = integer("max_heart_rate")
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var gender by Users.gender
    var height by Users.height
    var weight by Users.weight
    var age by Users.age
    var maxHeartRate by Users.maxHeartRate
}
