package de.mindmarket.ivyleemaster.di

import android.app.Application
import androidx.room.Room
import de.mindmarket.ivyleemaster.auth.data.AuthLocalDataSource
import de.mindmarket.ivyleemaster.core.data.database.IvyLeeDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun provideDatabase(application: Application) =
    Room.databaseBuilder(
        application,
        IvyLeeDatabase::class.java,
        "ivy_lee"
    )
        .fallbackToDestructiveMigrationFrom()
        .build()

fun provideDao(db:IvyLeeDatabase) =
    db.userDao

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }

    singleOf(::AuthLocalDataSource)
}