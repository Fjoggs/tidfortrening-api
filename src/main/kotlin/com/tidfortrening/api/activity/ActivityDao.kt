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
import javax.sql.DataSource

class ActivityDao (dataSource: DataSource) {

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
                    startDate = activityObject.startDate
                    endDate = activityObject.endDate
                    exercise = activityObject.exercise
                }
                activity.id.value
            }

    fun readActivity(id: Int): ActivityObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    ActivityObject(activity.startDate, activity.endDate, activity.exercise)
                }
            }

    fun updateActivity(id: Int, newActivity: ActivityObject): ActivityObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val activity = Activity.findById(id)
                activity?.let {
                    activity.startDate = newActivity.startDate
                    activity.endDate = newActivity.endDate
                    activity.exercise = newActivity.exercise
                    ActivityObject(activity.startDate, activity.endDate, activity.exercise)
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
