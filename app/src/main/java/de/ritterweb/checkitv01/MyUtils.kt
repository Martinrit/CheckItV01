package de.ritterweb.checkitv01

import java.text.SimpleDateFormat
import java.util.*

class MyUtils {

    private fun Date.toStringFormat(pattern: String = "dd.MM.yyyy"): String {
        return SimpleDateFormat(pattern).format(this)
    }
}