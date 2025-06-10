package de.syntax_institut.androidabschlussprojekt.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DreamImageDao {

    /*
    onConflict: .Ignore: Füge kein neues Objekt hinzu, wenn ein gleiches bereits existiert.
    Alternativ: .Replace überschreibt das aktuelle Objekt mit dem neuen Objekt.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dreamImage: DreamImage)

    @Query("SELECT * from dreams ORDER BY id ASC")          // Abfrage
    fun getAllItems(): Flow<List<DreamImage>>

    @Query("SELECT * from dreams WHERE id = :id")
    fun getItemById(id: String): Flow<DreamImage?>

    @Query("SELECT * from dreams WHERE date = :date")
    fun getDreamsByDate(date: Date): Flow<List<DreamImage>>

    @Query("SELECT * from dreams ORDER BY date ASC")
    fun getAllSortedAsc(): Flow<List<DreamImage>>

    @Query("SELECT * from dreams ORDER BY date DESC")
    fun getAllSortedDesc(): Flow<List<DreamImage>>

    @Query(
        """
        SELECT * from dreams
        WHERE mood IN (:moods)
        AND typeOfDream IN (:categories)
        AND imageStyle IN (:styles)
        ORDER BY
            CASE WHEN :sortAsc = 1 THEN date END ASC,
            CASE WHEN :sortAsc = 0 THEN date END DESC
    """
    )

    fun getFilteredAndSortedDreams(
        moods: List<String>?,
        categories: List<String>?,
        styles: List<String>?,
        sortAsc: Boolean
    ): Flow<List<DreamImage>>

    @Delete
    suspend fun delete(dreamImage: DreamImage)

    @Update
    suspend fun update(dreamImage: DreamImage)
}