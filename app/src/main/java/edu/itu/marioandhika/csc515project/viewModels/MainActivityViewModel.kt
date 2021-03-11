package edu.itu.marioandhika.csc515project.viewModels

import androidx.lifecycle.*
import edu.itu.marioandhika.csc515project.models.Entry
import edu.itu.marioandhika.csc515project.models.MyRepository

class MainActivityViewModel (private val repository: MyRepository): ViewModel() {
    val games: LiveData<List<Entry>> = repository.allEntries
}

class MainActivityViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
