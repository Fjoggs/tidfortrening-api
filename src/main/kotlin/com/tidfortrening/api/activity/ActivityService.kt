package com.tidfortrening.api.activity

import com.tidfortrening.api.activity.ActivityController.ActivityRequestObject
import com.tidfortrening.api.activity.ActivityDao.ActivityResponseObject

class ActivityService(private val activityDao: ActivityDao) {

    fun createActivity(exercise: ActivityRequestObject): Int? = activityDao.createActivity(exercise)

    fun readActivity(id: Int): ActivityResponseObject? = activityDao.readActivity(id)

    fun updateActivity(id: Int, newActivityRequest: ActivityRequestObject): ActivityRequestObject? =
            activityDao.updateActivity(id, newActivityRequest)

    fun deleteActivity(id: Int): Boolean = activityDao.deleteActivity(id)
}