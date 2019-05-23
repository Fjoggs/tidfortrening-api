package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.*

class ExerciseService(private val exerciseDao: ExerciseDao) {
    fun createExercise(exercise: ExerciseObject): Int = exerciseDao.createExercise(exercise)

    fun readExercise(exerciseId: Int): ExerciseObject? = exerciseDao.readExercise(exerciseId)
}