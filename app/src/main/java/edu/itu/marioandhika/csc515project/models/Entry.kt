package edu.itu.marioandhika.csc515project.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName="entries")
data class Entry(
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "message") val message: String?,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0 // or foodId: Int? = null
}

@Dao
interface EntryDAO{
    @Query("SELECT * FROM entries")
    fun getAll(): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE uid==:uid LIMIT 1")
    fun get(uid: Int):LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entry: Entry)

    @Update
    suspend fun update(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)

    @Query("DELETE FROM entries")
    suspend fun deleteAll()
}
