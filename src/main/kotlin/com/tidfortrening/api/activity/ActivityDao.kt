package com.tidfortrening.api.activity

import com.tidfortrening.api.activity.ActivityController.ActivityObject
import com.tidfortrening.api.exercise.Exercise
import com.tidfortrening.api.exercise.Exercises
import com.tidfortrening.api.user.User
import com.tidfortrening.api.user.Users
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import javax.sql.DataSource

class ActivityDao(dataSource: DataSource) {

    init {
        Database.connect(dataSource)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Activities)
        }
    }

    fun createActivity(activityObject: ActivityObject): Int =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.new {
                    startDate = DateTime.parse(activityObject.startDate)
                    endDate = DateTime.parse(activityObject.endDate)
                    exercise = getExercise(activityObject.exerciseId) ?: throw Exception("No exercise found")
                    users = getUsers(activityObject.users)
                }
                activity.id.value
            }

    private fun getExercise(exerciseId: String): Exercise? =
            transaction {
                Exercise.findById(exerciseId.toInt())
            }

    private fun getUsers(users: Array<String>): SizedIterable<User> {
        val userList = mutableListOf<Int>()
        users.forEach { userList.add(it.toInt()) }
        return transaction {
            User.forIds(userList)
        }
    }

    fun readActivity(id: Int): ActivityObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    ActivityObject(activity.startDate.toString(), activity.endDate.toString(), activity.exercise.id.toString(), activity.users.toList())
                }
            }

    fun updateActivity(id: Int, newActivity: ActivityObject): ActivityObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    activity.startDate = DateTime.parse(newActivity.startDate)
                    activity.endDate = DateTime.parse(newActivity.endDate)
                    activity.exercise = getExercise(newActivity.exerciseId) ?: throw Exception("No exercise found")
                    activity.users = getUsers(newActivity.users)
                    ActivityObject(activity.startDate.toString(), activity.endDate.toString(), activity.exercise.id.toString(), activity.users.toList())
                }
            }

    fun deleteActivity(id: Int): Boolean =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    activity.delete()
                    true
                } ?: false
            }
}

object Activities : IntIdTable() {
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
    val exercise = reference("exercise", Exercises)
}

object ActivityUsers: Table() {
    val activity = reference("activity", Activities).primaryKey(0)
    val user = reference("user", Users).primaryKey(1)
}

class Activity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Activity>(Activities)

    var startDate by Activities.startDate
    var endDate by Activities.endDate
    var exercise by Exercise referencedOn Activities.exercise
    var users by User via ActivityUsers
}
