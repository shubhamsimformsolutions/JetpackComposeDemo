package com.simform.jetcomposedemo.lesson_activities.login_ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simform.jetcomposedemo.R
import com.simform.jetcomposedemo.lesson_activities.login_ui.ui.theme.JetComposeDemoTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainViews()
        }
    }

    @Composable
    fun MainViews() {
        val focusManager = LocalFocusManager.current
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = colorResource(id = R.color.dark_purple)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.dark_purple),
                            colorResource(id = R.color.light_purple)
                        )
                    )
                )
        ) {
            Heading()
            UsernameField(focusManager)
            PasswordField(focusManager)
            LoginButton()
            RestViews()
        }
    }

    @Composable
    fun Heading() {
        Text(
            text = stringResource(R.string.sign_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 100.dp),
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(5.0f, 5.0f),
                    blurRadius = 2f
                )
            ),
            color = colorResource(id = R.color.bright_orange),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun UsernameField(focusManager: FocusManager) {

        var username by rememberSaveable { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }

        var orange = colorResource(id = R.color.bright_orange)
        var color by remember { mutableStateOf(Color.LightGray) }

        Column {
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = color
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = orange,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.bright_orange),
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.username),
                        color = Color.LightGray
                    )
                },
                textStyle = TextStyle(
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .onFocusChanged {
                        color =
                            if (it.isFocused) orange else Color.LightGray
                    },
                trailingIcon = {
                    if (isError)
                        Icon(Icons.Filled.Error, null, tint = MaterialTheme.colors.error)
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        isError = username.count() < 6
                        if (isError) {
                            color = Color.Red
                        }
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                isError = isError
            )
            if (isError) {
                Text(
                    text = stringResource(R.string.username_error),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 5.dp)

                )
            }
        }
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                .firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                .firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

    @Composable
    fun PasswordField(focusManager: FocusManager) {
        var showPassword by remember { mutableStateOf(false) }
        var password by rememberSaveable { mutableStateOf("") }
        var isError by rememberSaveable { mutableStateOf(false) }

        val orange = colorResource(id = R.color.bright_orange)
        var color by remember { mutableStateOf(Color.LightGray) }

        Column {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White
                ),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = orange,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = orange,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.Transparent
                ),
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = color
                    )
                },
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = null,
                                tint = color
                            )
                        }
                    } else {
                        IconButton(onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = color
                            )
                        }
                    }
                },
                modifier = Modifier
                    .onFocusChanged {
                        color =
                            if (it.isFocused) orange else Color.LightGray
                    }
                    .fillMaxWidth()
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                    .padding(
                        start = 30.dp,
                        top = 7.dp,
                        end = 30.dp
                    ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.password),
                        color = Color.LightGray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isError = !isValidPassword(password)
                        focusManager.clearFocus()
                    }
                ),
                isError = isError
            )
            if (isError) {
                Text(
                    text = stringResource(R.string.password_error),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .padding(start = 30.dp, top = 5.dp, bottom = 20.dp)

                )
            }
        }
    }

    @Composable
    fun LoginButton() {
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.bright_orange)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }

    @Composable
    fun RestViews() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        val mContext = LocalContext.current
        Column {
            Text(
                text = stringResource(R.string.forgot_password),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        //TODO: Onclick Sign UP
                        Toast
                            .makeText(
                                mContext,
                                resources.getString(R.string.forgot_password),
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val signup = stringResource(R.string.sign_up)
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.None,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                        )
                    ) {
                        append(stringResource(R.string.sign_msg_prefix))
                    }
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )
                    ) {
                        pushStringAnnotation(signup, signup)
                        append(signup)
                    }
                }
                ClickableText(text = annotatedString, onClick = { offset ->
                    annotatedString.getStringAnnotations(offset, offset)
                        .firstOrNull()?.let { span ->
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Sign Up")
                            }
                        }
                })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    JetComposeDemoTheme {
        val act: LoginActivity = LoginActivity()
        act.MainViews()
    }
}
