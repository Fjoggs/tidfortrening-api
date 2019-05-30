package com.tidfortrening.api.activity

import com.tidfortrening.api.activity.ActivityController.ActivityRequestObject
import com.tidfortrening.api.exercise.Exercise
import com.tidfortrening.api.exercise.ExerciseController.*
import com.tidfortrening.api.exercise.Exercises
import com.tidfortrening.api.user.User
import com.tidfortrening.api.user.UserController.UserJsonRequest
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
            SchemaUtils.create(ActivityUsers)
        }
    }

    fun createActivity(activityRequestObject: ActivityRequestObject): Int? {
        val activity = transaction {
            addLogger(StdOutSqlLogger)
            Activity.new {
                startDate = DateTime.parse(activityRequestObject.startDate)
                endDate = DateTime.parse(activityRequestObject.endDate)
                exercise = getExercise(activityRequestObject.exercise) ?: throw Exception("No exercise found")
            }
        }
        transaction {
            activity.users = getUsers(activityRequestObject.users)
        }
        return activity.id.value
    }


    fun readActivity(id: Int): ActivityResponseObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    ActivityResponseObject(
                            activity.startDate.toString(),
                            activity.endDate.toString(),
                            ExerciseJsonRequest(activity.exercise.name, activity.exercise.description),
                            parseUserList(activity.users)
                    )
                }
            }

    private fun parseUserList(users: SizedIterable<User>): List<UserJsonRequest> = users.map {
        UserJsonRequest(it.name, it.gender, it.height, it.weight, it.age, it.maxHeartRate)
    }

    private fun parseUsers(users: SizedIterable<User>): List<Int> = users.map { it.id.value }


    fun updateActivity(id: Int, newActivityRequest: ActivityRequestObject): ActivityRequestObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    activity.startDate = DateTime.parse(newActivityRequest.startDate)
                    activity.endDate = DateTime.parse(newActivityRequest.endDate)
                    activity.exercise = getExercise(newActivityRequest.exercise) ?: throw Exception("No exercise found")
                    activity.users = getUsers(newActivityRequest.users)
                    print("activity users ${activity.users}")
                    ActivityRequestObject(
                            activity.startDate.toString(),
                            activity.endDate.toString(),
                            activity.exercise.id.value,
                            parseUsers(activity.users)
                    )
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

    private fun getExercise(exerciseId: Int): Exercise? =
            transaction {
                Exercise.findById(exerciseId)
            }

    private fun getUsers(users: List<Int>): SizedIterable<User> =
            try {
                transaction {
                    User.forIds(users)
                }
            } catch (e: KotlinNullPointerException) {
                print("No user found");
                emptySized()
            }

    data class ActivityResponseObject(val startDate: String, val endDate: String, val exercise: ExerciseJsonRequest, val users: List<UserJsonRequest>)
}

object Activities : IntIdTable() {
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
    val exercise = reference("exercise", Exercises)
}

object ActivityUsers : Table() {
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


