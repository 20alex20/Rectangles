package com.example.rectangles

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun Greeting(n: Int) {
    val number = remember { mutableStateOf<Int>(0) }
    var index = 0
    val y_max = number.value / n + if(number.value % n != 0) 1 else 0
    val height = LocalConfiguration.current.screenHeightDp.dp
    Column {
        Column(modifier = Modifier
            .height(height - 75.dp)
            .verticalScroll(rememberScrollState())) {
            for (y in 1..y_max)
                Row(modifier = Modifier) {
                    for (x in 1..(if (number.value % n == 0 || y != y_max) n else number.value % n)) {
                        Box(
                            modifier = Modifier
                                .background(if (index % 2 == 0) Color.Red else Color.Blue)
                                .padding(vertical = 15.dp)
                                .fillMaxWidth(1f / (n + 1 - x)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("$index", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        index += 1
                    }
                }
        }
        Button(onClick = { number.value++ }, shape = RoundedCornerShape(0.dp), modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()) {
            Text(stringResource(R.string.add_rectangle), fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    //val retrofitController = RetrofitController("https://randomfox.ca/")
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = (1..100).toList()) {
            val link = remember { mutableStateOf<String>("https://randomfox.ca/images/23.jpg") }
            FoxPhotoCard(link)
            //getLink(retrofitController, link)
        }
    }
}

// проверено
@Composable
fun FoxPhotoCard(link: MutableState<String>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data("https://randomfox.ca/images/12.jpg")
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.fox_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/*fun getLink(retrofitController: RetrofitController, link: MutableState<String>) {
    CoroutineScope (Dispatchers.Main).launch {
        val linkResult = retrofitController.requestLink()
        when (linkResult) {
            is Result.Ok -> {
                link.value = linkResult.link.link
            }
            is Result.Error -> {

            }
        }
    }
}

// проверено
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

// проверено
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        // Button(onClick = retryAction) {
        // Text(stringResource(R.string.retry))
        // }
    }
}

 */
