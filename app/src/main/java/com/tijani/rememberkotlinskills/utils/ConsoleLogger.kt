package com.tijani.rememberkotlinskills.utils

import android.util.Log
import com.tijani.rememberkotlinskills.utils.mInterface.Logger

class ConsoleLogger : Logger {
    override fun logg() {
        Log.d("MyTagTest","logg()  called...")
    }
}