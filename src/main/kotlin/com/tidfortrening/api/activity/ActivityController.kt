package com.tidfortrening.api.activity

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activity")
class ActivityController {

    @PostMapping("/create")
    fun createExercise() = "Create"

    @GetMapping("/read")
    fun readExercise() = "Read"

    @PostMapping("/update")
    fun updateExercise() = "Update"

    @PostMapping("/delete")
    fun deleteExercise() = "Delete"

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"
}