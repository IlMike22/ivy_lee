package de.mindmarket.ivyleemaster.di

import com.google.firebase.auth.FirebaseAuth
import de.mindmarket.ivyleemaster.IvyLeeMasterApp
import de.mindmarket.ivyleemaster.add_idea.presentation.AddIdeaViewModel
import de.mindmarket.ivyleemaster.auth.data.AuthRemoteDataSource
import de.mindmarket.ivyleemaster.auth.data.UserAuthRepository
import de.mindmarket.ivyleemaster.auth.domain.AuthRepository
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginViewModel
import de.mindmarket.ivyleemaster.auth.register.presentation.RegisterViewModel
import de.mindmarket.ivyleemaster.idea.presentation.IdeaViewModel
import de.mindmarket.ivyleemaster.settings.SettingsViewModel
import de.mindmarket.ivyleemaster.task.data.IvyTaskRemoteDataSource
import de.mindmarket.ivyleemaster.task.data.IvyTaskRepository
import de.mindmarket.ivyleemaster.task.domain.IdeaRepository
import de.mindmarket.ivyleemaster.task.presentation.TaskViewModel
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

    // Auth
    singleOf(::AuthRemoteDataSource)
    singleOf(::UserAuthRepository).bind<AuthRepository>()
    singleOf(::FirebaseAuth)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)

    // Task
    singleOf(::IvyTaskRepository).bind<IdeaRepository>()
    singleOf(::IvyTaskRemoteDataSource)
    viewModelOf(::TaskViewModel)

    // Idea
    viewModelOf(::IdeaViewModel)

    // Add Idea
    viewModelOf(::AddIdeaViewModel)

    // Settings
    viewModelOf(::SettingsViewModel)
}