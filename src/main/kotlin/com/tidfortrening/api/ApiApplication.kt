package com.tidfortrening.api

import com.tidfortrening.api.activity.ActivityDao
import com.tidfortrening.api.activity.ActivityService
import com.tidfortrening.api.exercise.ExerciseDao
import com.tidfortrening.api.exercise.ExerciseService
import com.tidfortrening.api.user.UserDao
import com.tidfortrening.api.user.UserService
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

@SpringBootApplication
class ApiApplication : SpringBootServletInitializer() {

    fun dataSource(): DataSource {
        val dataSource = PGSimpleDataSource()
        dataSource.databaseName = "tidfortrening"
        dataSource.serverName = "localhost"
        dataSource.user = "tidfortrening"
        dataSource.password = javaClass.getResource("/static/database-password.txt").readText()
        return dataSource
    }

    fun activityDao() = ActivityDao(dataSource())

    fun exerciseDao() = ExerciseDao(dataSource())

    fun userDao() = UserDao(dataSource())

    @Bean
    fun activityService() = ActivityService(activityDao())

    @Bean
    fun exerciseService() = ExerciseService(exerciseDao())

    @Bean
    fun userService() = UserService(userDao())

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
            application.sources(ApiApplication::class.java)

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<ApiApplication>(*args)
        }
    }
}
