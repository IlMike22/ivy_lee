package de.mindmarket.ivyleemaster.di

import androidx.room.Room
import de.mindmarket.ivyleemaster.auth.data.AuthLocalDataSource
import de.mindmarket.ivyleemaster.core.data.database.IvyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            IvyDatabase::class.java,
            "ivy_db"
        ).build()
    }
    single { get<IvyDatabase>().userDao }

    singleOf(::AuthLocalDataSource)
}