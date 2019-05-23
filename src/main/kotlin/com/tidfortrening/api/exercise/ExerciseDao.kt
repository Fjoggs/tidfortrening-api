package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.ExerciseObject
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource


class ExerciseDao(dataSource: DataSource) {

    init {
        Database.connect(dataSource)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(ExerciseTable)
        }
    }

    fun createExercise(exerciseObject: ExerciseObject): Int {
        return transaction {
            addLogger(StdOutSqlLogger)
            val exercise = Exercise.new {
                exerciseName = exerciseObject.exerciseName
                exerciseDescription = exerciseObject.exerciseDescription
            }
            exercise.id.value
        }
    }

    fun readExercise(exerciseId: Int): ExerciseObject? =
            transaction {
                val exercise = Exercise.findById(exerciseId)
                exercise?.let {
                    ExerciseObject(exercise.exerciseName, exercise.exerciseDescription)
                }
            }
}

object ExerciseTable : IntIdTable() {
    val exerciseName = varchar("exercise_name", 50)
    val exerciseDescription = varchar("exercise_description", 200)
}

class Exercise(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, Exercise>(ExerciseTable)

    var exerciseName by ExerciseTable.exerciseName
    var exerciseDescription by ExerciseTable.exerciseDescription
}
