package com.example.loginscreencompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.loginscreencompose.ui.theme.LoginScreenComposeTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import kotlinx.coroutines.delay

const val TAG: String = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreenComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material.MaterialTheme.colors.background
                ) {
                    Login(getVideoUri())
                }
            }
        }
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("snow", "raw", packageName)
        val videoUri = "android.resource://$packageName/$rawId"
        return Uri.parse(videoUri)
    }
}


@Composable
fun Login(videoUri: Uri = Uri.parse("")) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
    val hazeState = remember { HazeState() }


    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        /*Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(factory = {
                context.buildPlayerView(exoPlayer)
            }, update = { styledPlayerView ->
                styledPlayerView.player = exoPlayer
            })
        }*/

        val images = listOf(
            R.drawable.plant,
            R.drawable.landscape,
            R.drawable.rpnickson,
            R.drawable.pexels_1,
            R.drawable.pexels_2,
            R.drawable.pexels_3,
            R.drawable.pexels_4,
            R.drawable.pexels_5,
            R.drawable.pexels_6,
        )

        val listState = rememberLazyListState()

        val infiniteImages =
            remember { images + images + images + images + images + images + images + images + images + images + images }  // List repeated several times

        /*delay(1600)

                               val targetIndex = (listState.firstVisibleItemIndex + 1) % infiniteImages.size
                               listState.scrollBy(2f) // Adjust the value (50f) to control the smoothness of the animation

                               listState.animateScrollToItem(
                                   targetIndex
                               )*/


        LaunchedEffect(Unit) {
            val ticker = kotlinx.coroutines.channels.ticker(delayMillis = 16) // ~60 FPS
            for (tick in ticker) {
                listState.scrollBy(2f) // Smoothly scroll by a small value
            }
        }




        LazyColumn(state = listState, modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { }
        }) {
            items(infiniteImages.size) { index ->
                Image(
                    painter = painterResource(id = infiniteImages[index]),
                    contentDescription = "Image $index",
                    modifier = Modifier
                        .fillMaxSize()
                        .haze(
                            hazeState,
                            backgroundColor = MaterialTheme.colorScheme.background,
                            tint = Color.Black.copy(alpha = 0.2f),
                            blurRadius = 15.dp
                        ),
                    contentScale = ContentScale.Crop
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent) // Fully transparent
                .pointerInput(Unit) { /* Consume all touch events */ }
        )









        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(24.dp)
                .align(Alignment.Center),
            hazeState = hazeState,
        )

        LoginViews(context, passwordFocusRequester, focusManager, Modifier.align(Alignment.Center))


    }


}


