package de.mindmarket.ivyleemaster.di

import com.google.firebase.auth.FirebaseAuth
import de.mindmarket.ivyleemaster.IvyLeeMasterApp
import de.mindmarket.ivyleemaster.auth.data.AuthRemoteDataSource
import de.mindmarket.ivyleemaster.auth.data.UserAuthRepository
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginViewModel
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as IvyLeeMasterApp).applicationScope
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    singleOf(::AuthRemoteDataSource)
    singleOf(::UserAuthRepository).bind<AuthRepository>()
    singleOf(::FirebaseAuth)
}