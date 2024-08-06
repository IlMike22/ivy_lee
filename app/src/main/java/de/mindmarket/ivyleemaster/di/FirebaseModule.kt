package de.mindmarket.ivyleemaster.di

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module

val firebaseModule = module {
    single {
        FirebaseApp.getInstance()
    }
    single {
        FirebaseAuth.getInstance()
    }
    single {
        FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL).reference
    }
}

private const val FIREBASE_DATABASE_URL = "https://ivyleemaster-default-rtdb.europe-west1.firebasedatabase.app"

