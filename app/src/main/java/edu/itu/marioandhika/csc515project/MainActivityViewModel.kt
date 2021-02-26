package edu.itu.marioandhika.csc515project

import androidx.lifecycle.*

class MainActivityViewModel(private val repository: MyRepository): ViewModel() {
    val games: LiveData<List<Game>> = repository.allGames
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