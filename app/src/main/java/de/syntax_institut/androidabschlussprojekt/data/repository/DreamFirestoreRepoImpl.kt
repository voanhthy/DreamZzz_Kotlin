package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.utils.helper.DateUtils.dateWithoutTimestampLong
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


class DreamFirestoreRepoImpl(
    private val firestore: FirebaseFirestore
): DreamFirestoreRepoInterface {

    private val TAG = "DreamFirestoreRepoImpl"


    private val _datesWithDreams = MutableStateFlow<Set<Long>>(emptySet())

    override fun getDatesWithDreams(): Flow<Set<Long>> {
        return _datesWithDreams
    }

    init {
        firestore.collection("dreams")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val dates = mutableSetOf<Long>()
                    for (doc in snapshot.documents) {
                        val dream = doc.toObject(DreamImage::class.java)

                        dream?.date?.let {
                            dates.add(dateWithoutTimestampLong(it))
                        }
                    }
                    _datesWithDreams.value = dates
                    Log.d(TAG, "Daten für datesWithDreams aktualisiert: ${dates.size} Einträge")
                }
            }
    }

    override fun getDreamsForDate(date: Date): Flow<List<DreamImage>> = callbackFlow {
        val startOfDay = date
        val endOfDay = Calendar.getInstance().apply {
            time = startOfDay
            add(Calendar.DAY_OF_YEAR, 1)    // ein Tag hinzufügen
            add(Calendar.MILLISECOND, -1)   // eine Millisek zurückgehen, um am Ende des Tages zu sein
        }.time

        // Listener für Firestore Snapshot Änderungen
        val listenerRegistration = firestore.collection("dreams")
            .whereGreaterThanOrEqualTo("date", startOfDay)
            .whereLessThanOrEqualTo("date", endOfDay)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    snapshot.toObjects(DreamImage::class.java)
                } else {
                    emptyList()
                }
            }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun addDream(dream: DreamImage): Result<Unit> {
        return try {
            val docRef = firestore.collection("dreams").document(dream.id.ifBlank { firestore.collection("dreams").document().id })
            val dreamToSave = dream.copy(id = docRef.id)

            docRef.set(dreamToSave).await()
            Log.d(TAG, "Traum erfolgreich hinzugefügt: ${dreamToSave.id}")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDream(dream: DreamImage): Result<Unit> {
        if (dream.id.isBlank()) {
            return Result.failure(IllegalArgumentException("ID darf nicht leer sein"))
        }
        return try {
            firestore.collection("dreams").document(dream.id).set(dream).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDream(dream: DreamImage): Result<Unit> {
        if (dream.id.isBlank()) {
            return Result.failure(IllegalArgumentException("ID darf nicht leer sein"))
        }
        return try {
            firestore.collection("dreams").document(dream.id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}