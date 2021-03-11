package edu.itu.marioandhika.csc515project.models

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MyRepository(private val entryDao: EntryDAO) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allEntries = entryDao.getAll()

//    fun getAllEntries():LiveData<List<Entry>> {
//        return entryDao.getAll()
//    }

    fun get(uid:Int):LiveData<List<Entry>> {
        return entryDao.get(uid)
    }

    @WorkerThread
    suspend fun update(entry: Entry) {
        entryDao.update(entry)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entry: Entry) {
        entryDao.insert(entry)
    }

    @WorkerThread
    suspend fun delete(entry: Entry) {
        entryDao.delete(entry)
    }
}
