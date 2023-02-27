package com.simform.jetcomposedemo.lesson_activities.constraint_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.simform.jetcomposedemo.R
import com.simform.jetcomposedemo.util.Constants

class ConstraintLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUI()
        }
    }
}

@Composable
fun MainUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        UiWithDefaultCompose()
        UiWithConstraintLayout()
    }
}

@Composable
fun UiWithDefaultCompose() {
    Column(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.compose_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.dark_purple),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.lightest_purple))
                .padding(10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Red)
                    .height(100.dp)
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
                    .height(100.dp)
            ) {}
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.Magenta)
                    .height(100.dp)
            ) {}

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .background(Color.Blue)
                    .height(100.dp)
            ) {}

            Box(
                modifier = Modifier
                    .width(130.dp)
                    .background(Color.Green)
                    .height(100.dp)
            ) {}
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .height(100.dp)
        ) {}
    }
}

@Composable
fun UiWithConstraintLayout() {
    val constraints = ConstraintSet {
        val title = createRefFor(Constants.TITLE_ID)
        val redBox = createRefFor(Constants.RED_BOX_ID)
        val yellowBox = createRefFor(Constants.YELLOW_BOX_ID)
        val magentaBox = createRefFor(Constants.MAGENTA_BOX_ID)
        val blueBox = createRefFor(Constants.BLUE_BOX_ID)
        val greenBox = createRefFor(Constants.GREEN_BOX_ID)
        val cyanBox = createRefFor(Constants.CYAN_BOX_ID)

        constrain(title) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(redBox) {
            top.linkTo(title.bottom)
            start.linkTo(parent.start)
            end.linkTo(yellowBox.start)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }

        constrain(yellowBox) {
            top.linkTo(title.bottom)
            start.linkTo(redBox.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }

        constrain(magentaBox) {
            top.linkTo(redBox.bottom)
            width = Dimension.value(50.dp)
            height = Dimension.value(100.dp)
        }

        constrain(blueBox) {
            top.linkTo(redBox.bottom)
            width = Dimension.value(90.dp)
            height = Dimension.value(100.dp)
        }

        constrain(greenBox) {
            top.linkTo(yellowBox.bottom)
            width = Dimension.value(130.dp)
            height = Dimension.value(100.dp)
        }

        createHorizontalChain(magentaBox, blueBox, greenBox, chainStyle = ChainStyle.Spread)

        constrain(cyanBox) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(blueBox.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }
    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.constraint_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.dark_purple),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(colorResource(id = R.color.lightest_purple))
                .padding(10.dp)
                .layoutId(Constants.TITLE_ID)
                .padding(vertical = 20.dp)
        )

        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId(Constants.RED_BOX_ID)
        ) {}

        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .layoutId(Constants.YELLOW_BOX_ID)
        ) {}

        Box(
            modifier = Modifier
                .background(Color.Magenta)
                .layoutId(Constants.MAGENTA_BOX_ID)
        ) {}

        Box(
            modifier = Modifier
                .background(Color.Blue)
                .layoutId(Constants.BLUE_BOX_ID)
        ) {}

        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId(Constants.GREEN_BOX_ID)
        ) {}
        Box(
            modifier = Modifier
                .background(Color.Cyan)
                .layoutId(Constants.CYAN_BOX_ID)
        ) {}
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MainUI()
}