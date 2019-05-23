package com.tidfortrening.api.activity

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource


class ActivityDao(dataSource: DataSource) {

    init {
        Database.connect(dataSource)
    }

    fun createActivity() {
        transaction {
            addLogger(StdOutSqlLogger)
        }
    }
}

object Activity: IntIdTable() {
    val exerciseId = integer("exercise_id").uniqueIndex()
    val personId = integer("exercise_id").uniqueIndex()
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
}
