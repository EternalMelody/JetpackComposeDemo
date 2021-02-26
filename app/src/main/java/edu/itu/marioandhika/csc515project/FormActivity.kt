package edu.itu.marioandhika.csc515project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import edu.itu.marioandhika.csc515project.ui.theme.CSC515ProjectTheme
import java.util.*

class FormActivity: AppCompatActivity() {
    private val model: FormActivityViewModel by viewModels {
        FormActivityViewModelFactory((application as BaseApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CSC515ProjectTheme {
                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(title = { Text("Enter Session Details:") },
                                navigationIcon = {
                                    IconButton(onClick={
                                        finish()
                                    }) {
                                        Icon(Icons.Default.ArrowBack)
                                    }
                                }) } ,
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                model.onSaveClicked()
                                Toast.makeText(this,"Session Posted", Toast.LENGTH_SHORT).show()
                                finish()
                            }) {
                                Text("+")
                            }
                        },
                        bodyContent = {
                            val psnId = model.psnId.value
                            val message = model.message.value
                            EntryForm(psnId, message, { model.onPsnIdChanged(it) }, { model.onMessageChanged(it) })
                        }
                )
            }
        }
    }
}

@Composable
fun EntryForm(psnId: String, message: String, onPsnIdChanged: (String) -> Unit, onMessageChanged: (String) -> Unit) {
    Card(shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp) {
        Row {
            Column {
                Text(text = "PSN ID")
                Text(text = "Message")
            }
            Column {
                TextField(value=psnId, onValueChange = {
                    onPsnIdChanged(it) })
                TextField(value=message, onValueChange = {
                    onMessageChanged(it) })
            }
        }
    }

}