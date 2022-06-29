package com.kevin.rhodesislandassist.util

import android.icu.util.GregorianCalendar
import java.util.*

fun getCurrentDate(): String {
    val calendar = GregorianCalendar()
    val time = Date()
    calendar.time = time
    return "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.DATE)}"
}