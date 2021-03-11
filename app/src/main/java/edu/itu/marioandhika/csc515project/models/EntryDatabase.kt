package edu.itu.marioandhika.csc515project.models

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.itu.marioandhika.csc515project.BaseApplication
import edu.itu.marioandhika.csc515project.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = arrayOf(Entry::class), version = 1, exportSchema = false)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EntryDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): EntryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryDatabase::class.java,
                    "entry_database"
                ).addCallback(EntryDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class EntryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.entryDao())
                }
            }
        }

        suspend fun populateDatabase(entryDao: EntryDAO) {
            // Delete all content here.
            entryDao.deleteAll()

            val game = Entry(Date().toString(), BaseApplication.instance.getString(R.string.lorem_ipsum))
            entryDao.insert(game)
            val game2 = Entry(Date().toString(),BaseApplication.instance.getString(R.string.lorem_ipsum))
            entryDao.insert(game2)
            val game3 = Entry(Date().toString(), BaseApplication.instance.getString(R.string.lorem_ipsum))
            entryDao.insert(game3)
        }
    }
}