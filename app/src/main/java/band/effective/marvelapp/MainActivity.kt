package band.effective.marvelapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import band.effective.marvelapp.data.getImages
import band.effective.marvelapp.ui.theme.MarvelAppTheme
import band.effective.marvelapp.ui.theme.backgroundColor
import band.effective.marvelapp.ui.theme.backgroundTriangleColors
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun DrawTriangle(color: MutableState<Color>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val trianglePath = Path().apply {
            moveTo(size.width, size.height / 2.5F)

            // 2)
            lineTo(size.width, size.height)

            // 3)
            lineTo(0f, size.height)
        }
        drawPath(trianglePath, color.value)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainApp() {
    Box(
        modifier = Modifier
            .background(color = backgroundColor)
    ) {
        val mutableStateOfTriangle = mutableStateOf(backgroundTriangleColors[0])
        DrawTriangle(color = mutableStateOfTriangle)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .matchParentSize()
        ) {
            Image(
                painter = painterResource(R.drawable.marvel_logo),
                contentDescription = "",
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 20.dp)
                    .fillMaxWidth(0.4F)
                    .size(100.dp, 30.dp)
            )
            Text(
                text = "Choose your hero",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 0.dp)
            )
            Heroes(mutableStateOfTriangle)
        }
    }
}

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalSnapperApi::class)
@Composable
fun Heroes(mutableStateOfTriangle: MutableState<Color>) {
    val lazyListState = rememberLazyListState()
    val layoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState)

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            // The scroll (fling) has finished, get the current item and
            // do something with it!
            val snappedItem = layoutInfo.currentItem
            // TODO: do something with snappedItem
            Log.d("Selected item", snappedItem.toString())
            if (snappedItem != null) {
                mutableStateOfTriangle.value = backgroundTriangleColors[snappedItem.index]
            }
        }
    }

    LazyRow(
        state = lazyListState,
        flingBehavior = rememberSnapperFlingBehavior(
            lazyListState = lazyListState,
            snapOffsetForItem = SnapOffsets.Center
        ),
        contentPadding = PaddingValues(start = 35.dp, top = 10.dp, end = 35.dp)
    ) {
        items(getImages()) { image ->
            Card(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 31.dp)
                    .fillMaxSize(0.97f)
                    .shadow(elevation = 10.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Box {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .width(300.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(image.id),
                        contentDescription = stringResource(id = image.id)
                    )
                    Text(
                        text = image.name,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.W700,
                            shadow = Shadow(
                                color = Color.Black,
                                blurRadius = 10f
                            )
                        ),
                        modifier = Modifier
                            .offset(x = 30.dp, y = 470.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MarvelAppTheme {
        MainApp()
    }
}
