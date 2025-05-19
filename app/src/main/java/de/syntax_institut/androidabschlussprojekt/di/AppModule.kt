package de.syntax_institut.androidabschlussprojekt.di

import android.R.attr.handle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax_institut.androidabschlussprojekt.data.local.dao.DreamImageDatabase
import de.syntax_institut.androidabschlussprojekt.data.remote.api.DreamImageApi
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoApiImpl
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import de.syntax_institut.androidabschlussprojekt.dataStore
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamDetailViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/*
Dependency Injection mit Koin
1. Setup (Gradle und Manifest)
2. In Screens koinViewModel() verwenden
3. In den ViewModels die Repos als Parameter einsetzen, damit sie injectet werden können
4. Dependencies in appModule definieren -> hier werden Dependencies aufgelöst
 */

// hier alle Abhängigkeiten hinzufügen
val appModule = module {

    // einmalige Dependency: API
    singleOf(DreamImageApi::retrofitService)

    // einmalige Dependency: Datenbank
    single {
        DreamImageDatabase.getDatabase(androidContext())
    }

    // einmalige Dependency: Dao
    single {
        get<DreamImageDatabase>().dreamImageDao()
    }

    // einmalige Dependency: DataStore
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    // Repository wiedergegeben als Typ RepoInterface
    single<DreamImageRepoInterface> {
        DreamImageRepoApiImpl(
            get()
        )
    }

    viewModelOf(::DreamViewModel)

    viewModelOf(::SettingsViewModel)

    viewModelOf(::DreamDetailViewModel)
}