package edu.itu.marioandhika.csc515project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Game::class), version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): GameDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).addCallback(GameDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class GameDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.gameDao())
                }
            }
        }

        suspend fun populateDatabase(gameDao: GameDAO) {
            // Delete all content here.
            gameDao.deleteAll()

            val game = Game("Mario","Romario")
            gameDao.insert(game)
            val game2 = Game("Zlatan","Ronaldo")
            gameDao.insert(game2)
            val game3 = Game("Ronaldinho","Zidane")
            gameDao.insert(game3)
        }
    }
}