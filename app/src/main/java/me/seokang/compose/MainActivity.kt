package me.seokang.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.seokang.compose.data.Message
import me.seokang.compose.data.SampleData
import me.seokang.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Conversation(messages = SampleData.conversationSample)
    }
}

@Composable
fun Message(name: String) {
    Text(
        text = name,
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable
fun MessageArea(text: String, isExpanded: Boolean) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp
    ) {
        Text(
            text = "Input text = ($text)",
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else 1 // If the message is expanded, we display all its content otherwise we only display the first line
        )
    }
}

@Composable
fun MessageCard(message: Message) {
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
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))  // Add a horizontal space between the image and the column

        var isExpanded by remember { mutableStateOf(false) } // We keep track if the message is expanded or not in this variable

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) { // We toggle the isExpanded variable when we click on this Column
            Message(name = message.title)
            Spacer(modifier = Modifier.height(4.dp)) // Add a vertical space between the Greeting and MessageCard texts
            MessageArea(text = message.body, isExpanded = isExpanded)
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message -> MessageCard(message = message) }
    }
}