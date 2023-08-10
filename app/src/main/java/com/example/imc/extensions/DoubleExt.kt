package com.example.imc.extensions

import android.widget.EditText

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)