package com.jroslar.listafacturasjetpackcomposev21.core

import android.content.Context

class Extensions {
    companion object {
        fun Int.getResourceStringAndroid(context: Context): String {
            return context.resources.getString(this)
        }
    }
}