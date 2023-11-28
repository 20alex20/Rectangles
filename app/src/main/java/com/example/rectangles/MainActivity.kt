package com.example.rectangles

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rectangles.network.OkHttpController
import com.example.rectangles.request.request


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            savedInstanceState?.let {
                it.getStringArray("links")!!.forEach { link ->
                    links.add(remember { mutableStateOf(link) })
                }
            }

            LazyVerticalStaggeredGrid(
                modifier = Modifier,
                columns = StaggeredGridCells.Adaptive(150.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(count = R.integer.limit, itemContent = { index ->
                        while (links.size <= index) {
                            links.add(remember { mutableStateOf("") })
                        }
                        request(links[index], okHttpController)
                        FoxPhotoCard(links[index], okHttpController, ::start)
                    })
                }
            )
        }
    }

    private val links: MutableList<MutableState<String>> = mutableListOf()

    private val okHttpController by lazy {
        OkHttpController("https://randomfox.ca")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("links", links.map { it.value }.toTypedArray())
        links.clear()
    }

    fun start(link: String) {
        val intent = Intent(this, SmallActivity::class.java)
        intent.putExtra("link", link)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoxPhotoCard(link: MutableState<String>, okHttpController: OkHttpController, start: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        onClick = {
            start(link.value)
        }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(link.value)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.fox_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
