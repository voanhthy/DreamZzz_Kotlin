package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import kotlinx.coroutines.flow.Flow
import java.util.Date


interface DreamFirestoreRepoInterface {

    // gibt einen Flow von normalisierten Zeitstempeln (Tagen), an denen Träume existieren, zurück
    fun getDatesWithDreams(): Flow<Set<Long>>

    //  Träume für einen bestimmten Tag abrufen
    fun getDreamsForDate(date: Date): Flow<List<DreamImage>>

    suspend fun addDream(dream: DreamImage): Result<Unit>
    suspend fun updateDream(dream: DreamImage): Result<Unit>
    suspend fun deleteDream(dream: DreamImage): Result<Unit>
}