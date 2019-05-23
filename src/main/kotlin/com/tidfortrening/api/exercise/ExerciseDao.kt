package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.ExerciseObject
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class ExerciseDao (dataSource: DataSource) {

    init {
        Database.connect(dataSource)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Exercises)
        }
    }

    fun createExercise(exerciseObject: ExerciseObject): Int =
            transaction {
                addLogger(StdOutSqlLogger)
                val exercise = Exercise.new {
                    name = exerciseObject.name
                    description = exerciseObject.description
                }
                exercise.id.value
            }

    fun readExercise(id: Int): ExerciseObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val exercise = Exercise.findById(id)
                exercise?.let {
                    ExerciseObject(exercise.name, exercise.description)
                }
            }

    fun updateExercise(id: Int, newExercise: ExerciseObject): ExerciseObject? =
            transaction {
                addLogger(StdOutSqlLogger)
                val exercise = Exercise.findById(id)
                exercise?.let {
                    exercise.name = newExercise.name
                    exercise.description = newExercise.description
                    ExerciseObject(exercise.name, exercise.description)
                }
            }

    fun deleteExercise(id: Int): Boolean =
            transaction {
                addLogger(StdOutSqlLogger)
                val exercise = Exercise.findById(id)
                exercise?.let {
                    exercise.delete()
                    true
                } ?: false
            }
}

object Exercises : IntIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 200)
}

class Exercise(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Exercise>(Exercises)

    var name by Exercises.name
    var description by Exercises.description
}
