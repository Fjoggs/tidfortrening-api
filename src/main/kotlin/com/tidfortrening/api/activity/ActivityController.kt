package com.tidfortrening.api.activity

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/activity")
class ActivityController(val activityService: ActivityService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody activityRequest: ActivityRequestObject): Int? {
        return activityService.createActivity(activityRequest)
    }

    @GetMapping("/read/{id}", produces = ["application/json"])
    fun readExercise(@PathVariable id: Int): String {
        val activity = activityService.readActivity(id)
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(activity)
    }

    @PostMapping("/update/{id}")
    fun updateExercise(@PathVariable id: Int, @RequestBody newActivityRequest: ActivityRequestObject): ActivityRequestObject? =
            activityService.updateActivity(id, newActivityRequest)

    @PostMapping("/delete/{id}")
    fun deleteExercise(@PathVariable id: Int): Boolean = activityService.deleteActivity(id)

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"

    data class ActivityRequestObject(val startDate: String, val endDate: String, val exercise: Int, val users: List<Int>)
}