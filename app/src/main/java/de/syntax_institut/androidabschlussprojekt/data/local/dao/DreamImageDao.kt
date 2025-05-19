package de.syntax_institut.androidabschlussprojekt.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import kotlinx.coroutines.flow.Flow

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
    fun getItemById(id: Long): Flow<DreamImage?>

    @Delete
    suspend fun delete(dreamImage: DreamImage)

    @Update
    suspend fun update(dreamImage: DreamImage)
}