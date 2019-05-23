package com.tidfortrening.api.activity

import com.tidfortrening.api.activity.ActivityController.*

class ActivityService(private val activityDao: ActivityDao) {

    fun createActivity(exercise: ActivityObject): Int = activityDao.createActivity(exercise)

    fun readActivity(id: Int): ActivityObject? = activityDao.readActivity(id)

    fun updateActivity(id: Int, newActivity: ActivityObject): ActivityObject? =
            activityDao.updateActivity(id, newActivity)

    fun deleteActivity(id: Int): Boolean = activityDao.deleteActivity(id)
}