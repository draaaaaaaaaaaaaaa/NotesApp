package com.armand.notesapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.armand.notesapp.data.entity.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Notes)

    @Query("SELECT * FROM notes_table")
    fun getAllData(): LiveData<List<Notes>>

    //livedata untuk memantau akativitas data sebelum dikirim ke activity
    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority() :LiveData<List<Notes>>

    @Query("SELECT * FROM notes_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority() : LiveData<List<Notes>>

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNote(note: Notes)

    @Query("SELECT * FROM notes_table WHERE title LIKE :query")
    fun searchByQuery(query: String) :LiveData<List<Notes>>
}



