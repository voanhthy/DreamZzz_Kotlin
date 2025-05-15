package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoApiImpl
import de.syntax_institut.androidabschlussprojekt.data.repository.DreamImageRepoInterface
import org.koin.dsl.module

/*
Dependency Injection mit Koin
1. Setup (Gradle und Manifest)
2. In Screens koinViewModel() verwenden
3. In den ViewModels die Repos als Parameter einsetzen, damit sie injectet werden können
4. Dependencies in appModule definieren -> hier werden Dependencies aufgelöst
 */

val appModule = module {

//    single<DreamImageRepoInterface> {
//        DreamImageRepoApiImpl(
//
//        )
//    }
}