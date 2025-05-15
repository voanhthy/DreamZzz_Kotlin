package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.remote.api.DreamImageApi
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoApiImpl
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf

/*
Dependency Injection mit Koin
1. Setup (Gradle und Manifest)
2. In Screens koinViewModel() verwenden
3. In den ViewModels die Repos als Parameter einsetzen, damit sie injectet werden können
4. Dependencies in appModule definieren -> hier werden Dependencies aufgelöst
 */

val appModule = module {

    singleOf(DreamImageApi::retrofitService)

    single<DreamImageRepoInterface> {
        DreamImageRepoApiImpl(
            get()
        )
    }

    viewModelOf(::DreamViewModel)
}