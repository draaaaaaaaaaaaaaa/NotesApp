package com.armand.notesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.armand.notesapp.data.NotesRepository
import com.armand.notesapp.data.entity.Notes
import com.armand.notesapp.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: NotesRepository = NotesRepository(notesDao)


    fun getAllData() : LiveData<List<Notes>> = repository.getAllAdata()
    fun insertData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    fun sortByHighPriority(): LiveData<List<Notes>> = notesDao.sortByHighPriority()
    fun sortByLowPriority(): LiveData<List<Notes>> = notesDao.sortByLowPriority()

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllData()
        }
    }

    fun searchByQuery(query: String): LiveData<List<Notes>>{
    return repository.searchByQuery(query)
    }
}