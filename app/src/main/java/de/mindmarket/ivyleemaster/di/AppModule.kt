package de.mindmarket.ivyleemaster.di

import de.mindmarket.ivyleemaster.IvyLeeMasterApp
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as IvyLeeMasterApp).applicationScope
    }
    viewModelOf(::LoginViewModel)
}