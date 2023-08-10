package com.example.imc.extensions

import android.widget.EditText

fun EditText.valueDouble() = text.toString().toDouble()
fun EditText.valueString() = text.toString()