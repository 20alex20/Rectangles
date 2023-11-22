package com.example.rectangles

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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


class MainActivity : ComponentActivity() {
    private var mutNum = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            mutNum = mutableStateOf(it.getInt("number"))
        }
        setContent {
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> Greeting(3, mutNum)
                Configuration.ORIENTATION_LANDSCAPE -> Greeting(4, mutNum)
                else -> Greeting(3, mutNum)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("number", mutNum.value)
    }
}

@Composable
fun Greeting(n: Int, mutNum: MutableState<Int>) {
    val number = remember { mutNum }

    var list: MutableList<Int> = ArrayList()
    for (i in 0 until n) {
        list.add(i)
    }

    val q = remember { mutableStateOf(-1) }
    val y_max = number.value / n + if(number.value % n != 0) 1 else 0
    val height = LocalConfiguration.current.screenHeightDp.dp
    Column {
        LazyColumn(modifier = Modifier
            .height(height - 75.dp)) {
            itemsIndexed(list) { index, item ->
                for (y in 1..y_max)
                    Row(modifier = Modifier) {
                        for (x in 1..(if (number.value % n == 0 || y != y_max) n else number.value % n)) {
                            var color: Color
                            if (q.value == index)
                                color = Color.Green
                            else if (index % 2 == 0)
                                color = Color.Red
                            else
                                color = Color.Blue
                            Box(
                                modifier = Modifier
                                    .background(color)
                                    .padding(vertical = 15.dp)
                                    .fillMaxWidth(1f / (n + 1 - x)).clickable { q.value = index },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("$index", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                            }
                        }
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
