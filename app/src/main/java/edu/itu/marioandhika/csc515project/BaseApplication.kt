package edu.itu.marioandhika.csc515project

import android.app.Application
import android.content.Context
import edu.itu.marioandhika.csc515project.models.EntryDatabase
import edu.itu.marioandhika.csc515project.models.MyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { EntryDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MyRepository(database.entryDao()) }


    companion object {
        lateinit var instance: BaseApplication private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}