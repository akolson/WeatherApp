package com.weather.app.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class Global {
    companion object {

        fun fromDateToDisplayDay(date: String): String {
            val localDate = fromDateToLocalDateTime(date)
            val dayOfWeek = localDate?.dayOfWeek?.name
            return dayOfWeek?.lowercase()?.replaceFirstChar(Char::titlecase) ?: ""
        }

        fun fromDateToLocalDateTime(date: String?): LocalDate? {
            return date?.split(" ")?.getOrNull(0)?.toLocalDate()
        }
    }
}