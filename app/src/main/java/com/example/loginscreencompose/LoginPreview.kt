package com.example.loginscreencompose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginscreencompose.ui.theme.HintColor
import com.example.loginscreencompose.ui.theme.Jost
import com.example.loginscreencompose.ui.theme.Primary
import com.google.accompanist.insets.navigationBarsWithImePadding

@Composable
@Preview
fun LoginPreview() {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
//    LoginViews(context, passwordFocusRequester, focusManager)
}

@Composable
fun LoginViews(
    context: Context,
    passwordFocusRequester: FocusRequester,
    focusManager: FocusManager,
    modifier: Modifier
) {


    Box(
        modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center

    ) {
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(50.dp)
                .fillMaxWidth(),

            ) {

            Column {
                Text(
                    "Login",
                    style = TextStyle(
                        fontFamily = Jost,
                        fontWeight = FontWeight.Normal,
                        fontSize = 30.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = Color.White,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                )

                Text(
                    "Welcome onboard with us!",
                    style = TextStyle(
                        fontFamily = Jost,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp, alignment = Alignment.CenterVertically
                )
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
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(fontSize = 10.sp, fontFamily = Jost, color = Color.White),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

            ClickableText(context, Modifier.padding(top = 10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append("New to Fenil? ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Register")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                            append(" Here")
                        }
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Jost,
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }


    }

}

@Composable
fun ClickableText(context: Context, modifier: Modifier) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Primary, RoundedCornerShape(10.dp))
            .clickable {
                context.doLogin()
            }
    ) {
        Text(
            "LOGIN",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Jost,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
        )
    }
}

private fun Context.doLogin() {
    Toast.makeText(
        this, "Something went wrong, try again later!", Toast.LENGTH_SHORT
    ).show()
}


@Composable
fun TextInput(
    inputType: InputType, focusRequester: FocusRequester? = null, keyboardActions: KeyboardActions
) {

    var value by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = inputType.label,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Jost,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        )


        TextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusOrder(focusRequester ?: FocusRequester())
                .border(
                    width = Dp.Hairline, brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.White.copy(alpha = .8f),
                            Color.White.copy(alpha = .2f),
                        ),
                    ), shape = RoundedCornerShape(12.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.White,
                backgroundColor = Color.White.copy(alpha = 0.2f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardOptions = inputType.keyboardOptions,
            visualTransformation = inputType.visualTransformation,
            keyboardActions = keyboardActions,
            textStyle = TextStyle(
                fontFamily = Jost,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text(
                    inputType.defaultValue, style = TextStyle(
                        color = HintColor.copy(alpha = 0.5f)
                    ), overflow = TextOverflow.Clip
                )
            }
        )
    }
}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation,
    val defaultValue: String
) {
    data object Name : InputType(
        label = "Username",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None,
        "Enter your username"
    )

    data object Password : InputType(
        label = "Password", icon = Icons.Default.Lock, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ), visualTransformation = PasswordVisualTransformation(), "Enter your password"
    )
}
