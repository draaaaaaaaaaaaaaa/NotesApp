package com.armand.notesapp.data

import androidx.lifecycle.LiveData
import com.armand.notesapp.data.entity.Notes
import com.armand.notesapp.data.room.NotesDao


class NotesRepository(private val notesDao: NotesDao) {

        fun getAllAdata() : LiveData<List<Notes>> = notesDao.getAllData()

        suspend fun insertNotes(notes: Notes) {
            notesDao.insertNote(notes)
        }


    fun sortByHighPriority(): LiveData<List<Notes>> = notesDao.sortByHighPriority()
    fun sortByLowPriority(): LiveData<List<Notes>> = notesDao.sortByLowPriority()

    suspend fun deleteAllData() = notesDao.deleteAllNote()


    fun searchByQuery(query: String) : LiveData<List<Notes>> {
        return notesDao.searchByQuery(query)
    }
    }


