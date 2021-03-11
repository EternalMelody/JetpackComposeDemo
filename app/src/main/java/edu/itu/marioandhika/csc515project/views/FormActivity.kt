package edu.itu.marioandhika.csc515project.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.itu.marioandhika.csc515project.BaseApplication
import edu.itu.marioandhika.csc515project.FormActivityViewModel
import edu.itu.marioandhika.csc515project.FormActivityViewModelFactory
import edu.itu.marioandhika.csc515project.views.StarAnimationDefinition.StarButtonState.*
import edu.itu.marioandhika.csc515project.ui.theme.CSC515ProjectTheme

class FormActivity: AppCompatActivity() {
    private var newMessage = true

    private val model: FormActivityViewModel by viewModels {
        FormActivityViewModelFactory((application as BaseApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newMessage = !intent.hasExtra("uid")
        if (!newMessage) {
            model.loadMessage(intent.getIntExtra("uid",-1))
        }
        setContent {
            CSC515ProjectTheme {
                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(title = {
                                if (newMessage) {
                                    Text("Add New Entry")
                                } else {
                                    Text("Edit Entry")
                                }},
                                navigationIcon = {
                                    IconButton(onClick={
                                        finish()
                                    }) {
                                        Icon(Icons.Default.ArrowBack)
                                    }
                                },
                                actions = {
                                    IconButton(onClick={
                                        model.deleteMessage()
                                        Toast.makeText(this@FormActivity, "Entry deleted", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }){
                                        Icon(Icons.Default.Delete)
                                    }
                                }
                            ) } ,
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                model.onSaveClicked()
                                Toast.makeText(this@FormActivity, "Entry saved", Toast.LENGTH_SHORT).show()
                                finish()
                            }) {
                                Icon(imageVector = Icons.Default.Add)
                            }
                        },
                        bodyContent = {
                            val message = model.message.value
                            EntryForm(message) { model.onMessageChanged(it) }
                        }
                )
            }
        }
    }

    @Composable
    fun EntryForm(message: String, onMessageChanged: (String) -> Unit) {
        Card(shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = model.date.value, textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp),
                    )
                    val state = remember { mutableStateOf(IDLE) }
                    Row(modifier = Modifier.height(80.dp).width(80.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedStarButton(
                            modifier = Modifier,
                            buttonState = state,
                            onToggle = {
                                state.value = if (state.value == IDLE) ACTIVE else IDLE
                            })
                    }
                }
                TextField(value=message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    maxLines=9,
                    singleLine=false,
                    label={Text("Enter text here")},
                    placeholder={Text("What did you do today?")},
                    onValueChange = {
                        onMessageChanged(it)
                    })
            }
        }
    }
}
