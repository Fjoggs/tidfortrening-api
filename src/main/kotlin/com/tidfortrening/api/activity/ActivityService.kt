package com.tidfortrening.api.activity

class ActivityService(private val activityDao: ActivityDao) {
    fun createActivity() = activityDao.createActivity()
}