package edu.itu.marioandhika.csc515project

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MyRepository(private val gameDao: GameDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allGames: LiveData<List<Game>> = gameDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }
}
