package edu.itu.marioandhika.csc515project.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.itu.marioandhika.csc515project.BaseApplication
import edu.itu.marioandhika.csc515project.R
import edu.itu.marioandhika.csc515project.viewModels.MainActivityViewModel
import edu.itu.marioandhika.csc515project.viewModels.MainActivityViewModelFactory
import edu.itu.marioandhika.csc515project.models.Entry
import edu.itu.marioandhika.csc515project.ui.theme.CSC515ProjectTheme
import java.util.*

class MainActivity : AppCompatActivity() {
    private val model: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as BaseApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CSC515ProjectTheme {
                // A surface container using the 'background' color from the theme
                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = { TopAppBar(title = {Text("My Diary")})  },
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = { FloatingActionButton(onClick = {
                            val intent = Intent(this, FormActivity::class.java)
                            startActivity(intent)
                        }){
                            Icon(imageVector = Icons.Default.Add)
                        } },
                        bodyContent = {
                            EntryList(model.games.observeAsState().value)
                        },
                        snackbarHost = {}
                )
            }
        }
    }

    @Composable
    fun EntryList(posts: List<Entry>?) {
        if (posts != null)
            LazyColumnFor(items = posts) {
                EntryCardView(it)
            }
    }

    @Composable
    fun EntryCardView(post: Entry) {
        Card(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(onClick = {
                    val intent = Intent(this, FormActivity::class.java)
                    intent.putExtra("uid", post.uid)
                    startActivity(intent)
                }),
            elevation = 8.dp
        ) {
            Row(modifier = Modifier
                .padding(8.dp)
                ) {
                Column() {
                    Text(modifier = Modifier.fillMaxWidth(),
                        text = post.date ?: "",
                        textAlign = TextAlign.End,
                        fontFamily = FontFamily.Monospace)
                    Divider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = post.message?:"",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CSC515ProjectTheme {
            EntryCardView(Entry(Date().toString(),getString(R.string.lorem_ipsum)))
        }
    }
}