package com.example.rectangles

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private var numberVar = 0

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> Greeting(3)
                Configuration.ORIENTATION_LANDSCAPE -> Greeting(4)
                else -> Greeting(3)
            }
        }
    }
}

@Composable
fun Greeting(n: Int) {
    val number = remember { mutableStateOf(numberVar) }
    var index = 0
    val y_max = number.value / n + if(number.value % n != 0) 1 else 0
    val height = LocalConfiguration.current.screenHeightDp.dp
    Column {
        Column(modifier = Modifier.height(height - 75.dp).verticalScroll(rememberScrollState())) {
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
        Button(onClick = { numberVar++; number.value = numberVar }, shape = RoundedCornerShape(0.dp), modifier = Modifier.height(75.dp).fillMaxWidth()) {
            Text(stringResource(R.string.add_rectangle), fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
    }
}
