package com.example.loginscreencompose

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.loginscreencompose.ui.theme.LoginScreenComposeTheme
import com.example.loginscreencompose.ui.theme.Shapes
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(factory = {
                context.buildPlayerView(exoPlayer)
            }, update = { styledPlayerView ->
                styledPlayerView.player = exoPlayer
            })
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .padding(24.dp)
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = 0.2f),
                    blurRadius = 30.dp
                )
        )

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)// Ensures the card takes the full width
                .align(Alignment.BottomCenter), // Aligns the card to the bottom center
            hazeState = hazeState,
        )

        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginViews(context, passwordFocusRequester, focusManager)
        }


    }


}

@Composable
fun LoginViews(
    context: Context, passwordFocusRequester: FocusRequester, focusManager: FocusManager
) {


    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter

    ) {

        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            TextInput(
                InputType.Name, keyboardActions = KeyboardActions(onNext = {
                    passwordFocusRequester.requestFocus()
                })
            )
            TextInput(
                InputType.Password, keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    context.doLogin()
                }), focusRequester = passwordFocusRequester
            )

            Button(
                onClick = {
                    context.doLogin()
                }, modifier = Modifier.fillMaxWidth()
            ) {
                androidx.compose.material.Text("SIGN IN", Modifier.padding(vertical = 8.dp))
            }
        }


    }

}

private fun Context.doLogin() {
    Toast.makeText(
        this, "Something went wrong, try again later!", Toast.LENGTH_SHORT
    ).show()
}

private fun Context.buildExoPlayer(uri: Uri) = ExoPlayer.Builder(this).build().apply {
    setMediaItem(MediaItem.fromUri(uri))
    repeatMode = Player.REPEAT_MODE_ALL
    playWhenReady = true
    prepare()
}

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) = StyledPlayerView(this).apply {
    player = exoPlayer
    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    useController = false
    resizeMode = RESIZE_MODE_ZOOM
}

@Composable
fun TextInput(
    inputType: InputType, focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions
) {

    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .focusOrder(focusRequester ?: FocusRequester()),
        leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { androidx.compose.material.Text(text = inputType.label) },
        shape = Shapes.small,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    data object Name : InputType(
        label = "Username",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    data object Password : InputType(
        label = "Password", icon = Icons.Default.Lock, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation()
    )
}


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun GlassCard(
    modifier: Modifier, hazeState: HazeState
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(250.dp)
        .clickable {

        }
        // No offset applied, card doesn't move on click
        .hazeChild(state = hazeState, shape = RoundedCornerShape(12.dp))
        .border(
            width = Dp.Hairline, brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = .8f),
                    Color.White.copy(alpha = .2f),
                ),
            ), shape = RoundedCornerShape(12.dp)
        )) {

    }
}