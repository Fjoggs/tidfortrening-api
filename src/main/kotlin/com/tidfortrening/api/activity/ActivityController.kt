package com.tidfortrening.api.activity

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/activity")
class ActivityController(val activityService: ActivityService) {

    @PostMapping("/create")
    fun createExercise(@RequestBody activity: ActivityObject): Int {
        return activityService.createActivity(activity)
    }

    @GetMapping("/read/{id}")
    fun readExercise(@PathVariable id: Int): ActivityObject? = activityService.readActivity(id)

    @PostMapping("/update/{id}")
    fun updateExercise(@PathVariable id: Int, @RequestBody newActivity: ActivityObject): ActivityObject? =
            activityService.updateActivity(id, newActivity)

    @PostMapping("/delete/{id}")
    fun deleteExercise(@PathVariable id: Int): Boolean = activityService.deleteActivity(id)

    @GetMapping("/greeting")
    fun greeting() = "Oh herro"

    data class ActivityObject(val startDate: String, val endDate: String, val exerciseId: String, val users: Array<String>)
}