package com.simform.jetcomposedemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simform.jetcomposedemo.lesson_activities.constraint_layout.ConstraintLayoutActivity
import com.simform.jetcomposedemo.lesson_activities.login_ui.LoginActivity
import com.simform.jetcomposedemo.models.ActivityType
import com.simform.jetcomposedemo.models.LessonModel
import com.simform.jetcomposedemo.models.getLessons
import com.simform.jetcomposedemo.ui.theme.JetComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn {
                items(getLessons().size) {
                    LessonCard(lesson = getLessons()[it])
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: LessonModel) {
    val mContext = LocalContext.current
    var isShowing by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(20.dp)
            .shadow(
                15.dp,
                shape = RoundedCornerShape(corner = CornerSize(20.dp)),
                ambientColor = Color.Black
            )
            .clip(RoundedCornerShape(corner = CornerSize(size = 20.dp)))
            .background(colorResource(id = R.color.dark_purple))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = lesson.title,
                color = if (isShowing) colorResource(id = R.color.dark_purple) else Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isShowing) colorResource(id = R.color.lightest_purple) else colorResource(
                            id = R.color.dark_purple
                        )
                    )
                    .padding(10.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        isShowing = !isShowing
                    }
            )
            AnimatedVisibility(visible = isShowing) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = lesson.description,
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp)

                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Column() {
                                for (item in lesson.topics) {
                                    Row(modifier = Modifier.fillMaxSize()) {
                                        Text(
                                            text = "➼",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(horizontal = 5.dp)
                                        )
                                        Text(
                                            text = "$item",
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = {
                            mContext.startActivity(getIntent(mContext, lesson.activity))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(text = "Redirect")
                    }
                }
            }
        }
    }
}

fun getIntent(context: Context, activityType: ActivityType): Intent? {
    return when (activityType) {
        ActivityType.LOGIN_ACTIVITY -> Intent(context, LoginActivity::class.java)
        ActivityType.CONSTRAINT_LAYOUT_ACTIVITY -> Intent(context, ConstraintLayoutActivity::class.java)
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