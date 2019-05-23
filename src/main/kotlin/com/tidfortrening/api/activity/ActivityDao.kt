package com.tidfortrening.api.activity

import com.tidfortrening.api.activity.ActivityController.ActivityObject
import com.tidfortrening.api.exercise.Exercise
import com.tidfortrening.api.exercise.Exercises
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.lang.Exception
import java.time.format.DateTimeParseException
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
                }
                activity.id.value
            }

    private fun getExercise(exerciseId: String): Exercise? =
            transaction {
                Exercise.findById(exerciseId.toInt())
            }


    fun readActivity(id: Int): ActivityObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    ActivityObject(activity.startDate.toString(), activity.endDate.toString(), activity.exercise.id.toString())
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
                    ActivityObject(activity.startDate.toString(), activity.endDate.toString(), activity.exercise.id.toString())
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

class Activity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Activity>(Activities)

    var startDate by Activities.startDate
    var endDate by Activities.endDate
    var exercise by Exercise referencedOn Activities.exercise
}
