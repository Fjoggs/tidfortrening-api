package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.*

class ExerciseService(private val exerciseDao: ExerciseDao) {

    fun createExercise(exercise: ExerciseObject): Int = exerciseDao.createExercise(exercise)

    fun readExercise(exerciseId: Int): ExerciseObject? = exerciseDao.readExercise(exerciseId)

    fun updateExercise(exerciseId: Int, newExercise: ExerciseObject): ExerciseObject? =
            exerciseDao.updateExercise(exerciseId, newExercise)

    fun deleteExercise(exerciseId: Int): Boolean = exerciseDao.deleteExercise(exerciseId)
}