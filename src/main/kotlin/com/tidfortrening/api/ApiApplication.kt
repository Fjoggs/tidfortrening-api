package com.tidfortrening.api

import com.tidfortrening.api.exercise.ExerciseDao
import com.tidfortrening.api.exercise.ExerciseService
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

    fun exerciseDao() = ExerciseDao(dataSource())

    @Bean
    fun exerciseService() = ExerciseService(exerciseDao())

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
            application.sources(ApiApplication::class.java)

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<ApiApplication>(*args)
        }
    }
}
