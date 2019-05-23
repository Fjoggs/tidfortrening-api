package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.*

class ExerciseService(private val exerciseDao: ExerciseDao) {

    fun createExercise(exercise: ExerciseJsonRequest): Int = exerciseDao.createExercise(exercise)

    fun readExercise(id: Int): ExerciseJsonRequest? = exerciseDao.readExercise(id)

    fun updateExercise(id: Int, newExercise: ExerciseJsonRequest): ExerciseJsonRequest? =
            exerciseDao.updateExercise(id, newExercise)

    fun deleteExercise(id: Int): Boolean = exerciseDao.deleteExercise(id)
}