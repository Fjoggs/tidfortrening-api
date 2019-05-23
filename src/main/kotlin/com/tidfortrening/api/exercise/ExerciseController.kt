package com.tidfortrening.api.exercise

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/exercise")
class ExerciseController(val exerciseService: ExerciseService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody exercise: ExerciseObject): Int {
        return exerciseService.createExercise(exercise)
    }

    @GetMapping("/read/{exerciseId}")
    fun readExercise(@PathVariable exerciseId: Int): ExerciseObject? = exerciseService.readExercise(exerciseId)

    @PostMapping("/update/{exerciseId}")
    fun updateExercise(@PathVariable exerciseId: Int, @RequestBody newExercise: ExerciseObject): ExerciseObject? =
            exerciseService.updateExercise(exerciseId, newExercise)

    @PostMapping("/delete/{exerciseId}")
    fun deleteExercise(@PathVariable exerciseId: Int): Boolean = exerciseService.deleteExercise(exerciseId)

    @GetMapping("/greeting")
    fun greeting() = "Oh herroht"

    data class ExerciseObject(val exerciseName: String, val exerciseDescription: String)
}