package com.tidfortrening.api.exercise

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/exercise")
class ExerciseController(val exerciseService: ExerciseService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody exercise: ExerciseObject) {
        exerciseService.createExercise(exercise)
    }

    @GetMapping("/read")
    fun readExercise() = "Read"

    @PostMapping("/update")
    fun updateExercise() = "Update"

    @PostMapping("/delete")
    fun deleteExercise() = "Delete"

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"

    data class ExerciseObject(val exerciseName: String, val exerciseDescription: String)
}