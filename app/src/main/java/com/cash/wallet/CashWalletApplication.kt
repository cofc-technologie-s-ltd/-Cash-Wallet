package com.cash.wallet

import android.app.Application
import android.content.Context

class CashWalletApplication : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}
