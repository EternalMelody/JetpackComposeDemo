package edu.itu.marioandhika.csc515project

import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Room

@Entity(tableName="game")
data class Game(
    @ColumnInfo(name = "psn_id") val psnId: String?,
    @ColumnInfo(name = "message") val message: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0 // or foodId: Int? = null
}

@Dao
interface GameDAO{
    @Query("SELECT * FROM game")
    fun getAll(): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: Game)

    @Query("DELETE FROM game")
    suspend fun deleteAll()
}
