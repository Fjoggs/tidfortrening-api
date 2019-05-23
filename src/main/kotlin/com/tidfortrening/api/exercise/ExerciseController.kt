package com.tidfortrening.api.exercise

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/exercise")
class ExerciseController(val exerciseService: ExerciseService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody exercise: ExerciseObject): Int {
        return exerciseService.createExercise(exercise)
    }

    @GetMapping("/read/{id}")
    fun readExercise(@PathVariable id: Int): ExerciseObject? = exerciseService.readExercise(id)

    @PostMapping("/update/{id}")
    fun updateExercise(@PathVariable id: Int, @RequestBody newExercise: ExerciseObject): ExerciseObject? =
            exerciseService.updateExercise(id, newExercise)

    @PostMapping("/delete/{id}")
    fun deleteExercise(@PathVariable id: Int): Boolean = exerciseService.deleteExercise(id)

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"

    data class ExerciseObject(val name: String, val description: String)
}