package edu.itu.marioandhika.csc515project

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FormActivityViewModel(private val repository: MyRepository): ViewModel() {
    val psnId: MutableState<String> = mutableStateOf("")
    val message: MutableState<String> = mutableStateOf("")

    fun onPsnIdChanged(s: String){
        this.psnId.value = s
    }

    fun onMessageChanged(s: String){
        this.message.value = s
    }

    fun onSaveClicked(){
        runBlocking {
            launch{
                repository.insert(Game(psnId = psnId.value, message = message.value))
            }
        }
    }
}

class FormActivityViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
