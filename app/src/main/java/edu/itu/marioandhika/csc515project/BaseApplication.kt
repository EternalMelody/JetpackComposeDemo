package edu.itu.marioandhika.csc515project

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { GameDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MyRepository(database.gameDao()) }
}