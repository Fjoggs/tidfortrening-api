package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.*

class ExerciseService(private val exerciseDao: ExerciseDao) {

    fun createExercise(exercise: ExerciseObject): Int = exerciseDao.createExercise(exercise)

    fun readExercise(id: Int): ExerciseObject? = exerciseDao.readExercise(id)

    fun updateExercise(id: Int, newExercise: ExerciseObject): ExerciseObject? =
            exerciseDao.updateExercise(id, newExercise)

    fun deleteExercise(id: Int): Boolean = exerciseDao.deleteExercise(id)
}