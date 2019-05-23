package com.tidfortrening.api.exercise

import com.tidfortrening.api.exercise.ExerciseController.*

class ExerciseService(private val exerciseDao: ExerciseDao) {
    fun createExercise(exercise: ExerciseObject) =
            exerciseDao.createExercise(exercise)
}