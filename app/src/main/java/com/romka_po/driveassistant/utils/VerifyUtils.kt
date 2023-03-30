package com.romka_po.driveassistant.utils

import java.util.regex.Pattern

class VerifyUtils {

    fun checkEmail(string: String): Boolean {
        return Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\$").matcher(string).matches()
    }
    fun checkName(string: String): Boolean {
        return Pattern.compile("^[a-zA-Zа-яёА-ЯЁ0-9_-]{2,15}\$").matcher(string).matches()
    }
}