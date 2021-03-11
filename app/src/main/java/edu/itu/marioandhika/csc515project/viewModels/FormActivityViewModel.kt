package edu.itu.marioandhika.csc515project

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.itu.marioandhika.csc515project.models.Entry
import edu.itu.marioandhika.csc515project.models.MyRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class FormActivityViewModel(private val repository: MyRepository): ViewModel() {
    var newMessage = true
    val message: MutableState<String> = mutableStateOf("")
    val date: MutableState<String> = mutableStateOf(Date().toString())

    private var entry = Entry("","")

    fun loadMessage(uid:Int){
        newMessage = false
        entry = repository.allEntries.value?.first { it.uid == uid }?:Entry("","Somehownotfound")
        message.value = entry.message?:"Notfound"
        date.value = entry.date?:"NotFound"
    }

    fun deleteMessage(){
        if (!newMessage) {
            runBlocking {
                launch {
                    repository.delete(entry)
                }
            }
        }
    }

    fun onMessageChanged(s: String){
        this.message.value = s
    }

    fun onSaveClicked(){
        runBlocking {
            launch{
                if (newMessage) {
                    repository.insert(Entry(date = Date().toString(), message = message.value))
                } else {
                    val newEntry = Entry(entry.date,message.value)
                    newEntry.uid = entry.uid
                    repository.update(newEntry)
                }
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
