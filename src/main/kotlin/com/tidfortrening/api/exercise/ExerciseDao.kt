package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.ExerciseObject
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource


class ExerciseDao(dataSource: DataSource) {

    init {
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(ExerciseTable)
        }
    }

    fun createExercise(exercise: ExerciseObject) {
        transaction {
            addLogger(StdOutSqlLogger)
            ExerciseTable.insert {
                it[exerciseName] = exercise.exerciseName
                it[exerciseDescription] = exercise.exerciseDescription
            }
        }
    }
}

object ExerciseTable: IntIdTable() {
    val exerciseName = varchar("exercise_name", 50)
    val exerciseDescription = varchar("exercise_description", 200)
}
