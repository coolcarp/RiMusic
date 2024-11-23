package it.fast4x.rimusic.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import it.fast4x.rimusic.enums.MusicAnimationType
import kotlinx.coroutines.launch

@Composable
fun MusicAnimation(
    color: Color,
    modifier: Modifier = Modifier,
    barWidth: Dp = 4.dp,
    cornerRadius: Dp = 16.dp,
    type: MusicAnimationType = MusicAnimationType.Bars,
    show: Boolean = true
) {
    if (!show) return

    val animatablesWithSteps = remember {
        listOf(
            Animatable(0f) to listOf(
                0.2f,
                0.8f,
                0.1f,
                0.1f,
                0.3f,
                0.1f,
                0.2f,
                0.8f,
                0.7f,
                0.2f,
                0.4f,
                0.9f,
                0.7f,
                0.6f,
                0.1f,
                0.3f,
                0.1f,
                0.4f,
                0.1f,
                0.8f,
                0.7f,
                0.9f,
                0.5f,
                0.6f,
                0.3f,
                0.1f
            ),
            Animatable(0f) to listOf(
                0.2f,
                0.5f,
                1.0f,
                0.5f,
                0.3f,
                0.1f,
                0.2f,
                0.3f,
                0.5f,
                0.1f,
                0.6f,
                0.5f,
                0.3f,
                0.7f,
                0.8f,
                0.9f,
                0.3f,
                0.1f,
                0.5f,
                0.3f,
                0.6f,
                1.0f,
                0.6f,
                0.7f,
                0.4f,
                0.1f
            ),
            Animatable(0f) to listOf(
                0.6f,
                0.5f,
                1.0f,
                0.6f,
                0.5f,
                1.0f,
                0.6f,
                0.5f,
                1.0f,
                0.5f,
                0.6f,
                0.7f,
                0.2f,
                0.3f,
                0.1f,
                0.5f,
                0.4f,
                0.6f,
                0.7f,
                0.1f,
                0.4f,
                0.3f,
                0.1f,
                0.4f,
                0.3f,
                0.7f
            )
        )
    }

    LaunchedEffect(Unit) {
        animatablesWithSteps.forEach { (animatable, steps) ->
            launch {
                while (true) {
                    steps.forEach { step ->
                        animatable.animateTo(step)
                    }
                }
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        animatablesWithSteps.forEach { (animatable) ->
            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(barWidth)
            ) {
                when (type) {
                    MusicAnimationType.Bubbles -> drawCircle(
                        color = color,
                        radius = animatable.value * (size.height/2)
                    )
                    MusicAnimationType.Bars -> drawRoundRect(
                        color = color,
                        topLeft = Offset(x = 0f, y = size.height * (1 - animatable.value)),
                        size = size.copy(height = animatable.value * size.height),
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                    MusicAnimationType.CrazyBars, MusicAnimationType.CrazyPoints -> drawLine(
                        color = color,
                        start = Offset(x = 0f, y = animatable.value * (size.height/2)),
                        end = Offset(x = animatable.value * (size.height/2), y = if (type == MusicAnimationType.CrazyBars) size.height else animatable.value * (size.height/2)),
                        strokeWidth = size.width
                    )
                }


            }
        }
    }
}
