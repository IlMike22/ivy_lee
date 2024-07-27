package de.mindmarket.ivyleemaster

import android.app.Application
import de.mindmarket.ivyleemaster.di.appModule
import de.mindmarket.ivyleemaster.di.databaseModule
import de.mindmarket.ivyleemaster.di.firebaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class IvyLeeMasterApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@IvyLeeMasterApp)
            modules(appModule, firebaseModule, databaseModule)
        }
    }
}