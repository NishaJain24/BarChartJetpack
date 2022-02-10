package com.nishajain.barchartjetpack

import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nishajain.barchartjetpack.ui.theme.BarChartJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarChartJetpackTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Bar Chart",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            BarChart()
                        }
                    }
                }
            }
        }
    }
}

private fun identifyCLickItem(
    points: List<Point>,
    x: Float,
    y: Float
): Int {
    for ((index, point) in points.withIndex()) {
        if (x > point.x + 20 && x < point.x + 20 + 40) {
            return index
        }
    }
    return -1
}

@Composable
fun BarChart() {
    val point = listOf(
        Point(10, 200),
        Point(90, 100),
        Point(170, 40),
        Point(250, 200),
        Point(330, 120),
        Point(410, 100),
        Point(490, 200),
    )

    val context = LocalContext.current
    var start by remember { mutableStateOf(false) }
    val heightPre by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = FloatTweenSpec(duration = 1000)
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Yellow).padding(40.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        val i = identifyCLickItem(point, it.x, it.y)
                        Toast
                            .makeText(context, "onTap: $i", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }
    ) {
        drawLine(
            start = Offset(10f, 600f),
            end = Offset(10f, 0f),
            color = Color.Black,
            strokeWidth = 2f
        )
        drawLine(
            start = Offset(10f, 600f),
            end = Offset(600f, 600f),
            color = Color.Black,
            strokeWidth = 1f
        )
        start = true

        for (p in point) {
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(p.x + 30f, 600 - (600 - p.y) * heightPre),
                size = Size(55f, (600 - p.y) * heightPre)
            )
        }
    }
}

