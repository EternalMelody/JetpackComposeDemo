package edu.itu.marioandhika.csc515project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import edu.itu.marioandhika.csc515project.ui.theme.CSC515ProjectTheme

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
                        topBar = { TopAppBar(title = {Text("My LFG App")})  },
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = { FloatingActionButton(onClick = {
                            val intent = Intent(this, FormActivity::class.java)
                            startActivityForResult(intent, 0)
                        }){
                            Text("+")
                        } },
                        bodyContent = {
                            val posts = model.games.observeAsState().value
                            GameList(posts)
                        }
                )
            }
        }
    }
}

@Composable
fun GameList(posts: List<Game>?) {
    if (posts != null)
        LazyColumnFor(items = posts) {
            Game(it)
        }
}

@Composable
fun Game(post: Game) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column {
                Text(text = "PSN ID")
                Text(text = "Message")
            }
            Column {
                Text(text = post.psnId?:"")
                Text(text = post.message?:"")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CSC515ProjectTheme {
        Game(Game("MyPSNID","No message"))
    }
}