package com.simform.jetcomposedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simform.jetcomposedemo.login_ui.LoginActivity
import com.simform.jetcomposedemo.ui.theme.JetComposeDemoTheme
import com.simform.jetcomposedemo.util.ActivityType
import kotlin.contracts.Returns

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .border(1.dp, Color.Red, RectangleShape)
                            .fillMaxWidth()
                            .padding(20.dp)) {
                        getButton(text = getString(R.string.lesson_1_ui_practice))
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun getButton(text: String) {

    val mContext = LocalContext.current

    Button(
        onClick = {
            mContext.startActivity(getIntent(mContext, ActivityType.LOGIN_ACTIVITY))
        },
        Modifier.padding(10.dp),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow
        ),

    )
    {
        Text(text = text, color = Color.Black)
    }
}

fun getIntent(context: Context, activityType: ActivityType): Intent? {
    return when (activityType) {
        ActivityType.LOGIN_ACTIVITY -> Intent(context, LoginActivity::class.java)
        else -> { // Note the block
            null
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetComposeDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
        }
    }
}