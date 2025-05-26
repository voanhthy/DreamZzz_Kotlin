package de.syntax_institut.androidabschlussprojekt.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.syntax_institut.androidabschlussprojekt.data.local.model.DreamImage
import de.syntax_institut.androidabschlussprojekt.utils.helper.DateConverter

// eine Tabelle mit entity DreamImage
@Database(entities = [DreamImage::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DreamImageDatabase: RoomDatabase() {
    abstract fun dreamImageDao(): DreamImageDao

    companion object {
        @Volatile
        private var Instance: DreamImageDatabase? = null

        // gibt Datenbank-Instanz zurück, erstellt Datenbank und sorgt dafür, dass auch wirklich nur eine Instanz der Datenbank existiert
        fun getDatabase(context: Context): DreamImageDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DreamImageDatabase::class.java, "dreamImage_database")
                    .build().also { Instance = it }
            }
        }
    }
}
