package me.seokang.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.seokang.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Row(modifier = Modifier.padding(all = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_picture),
                            contentDescription = "Profile Image"
                        )
                        Column {
                            Greeting(name = "Android")
                            MessageCard("SeoKang");
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Row(
            modifier = Modifier.padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape) //Set image size to 40 dp and shaped as a circle
            )
            
            Spacer(modifier = Modifier.width(8.dp))  // Add a horizontal space between the image and the column
            
            Column {
                Greeting(name = "Android")
                Spacer(modifier = Modifier.height(4.dp)) // Add a vertical space between the Greeting and MessageCard texts
                MessageCard("SeoKang");
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MessageCard(text: String) {
    Text(text = "Input text = ($text)")
}