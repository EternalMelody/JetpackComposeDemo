package edu.itu.marioandhika.csc515project.views

import android.annotation.SuppressLint
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import edu.itu.marioandhika.csc515project.views.StarAnimationDefinition.StarButtonState.*
import edu.itu.marioandhika.csc515project.views.StarAnimationDefinition.starColor
import edu.itu.marioandhika.csc515project.views.StarAnimationDefinition.starSize
import edu.itu.marioandhika.csc515project.views.StarAnimationDefinition.starTransitionDefinition

@Composable
fun AnimatedStarButton(
    modifier: Modifier,
    buttonState: MutableState<StarAnimationDefinition.StarButtonState>,
    onToggle: () -> Unit
) {
    val toState = if (buttonState.value == IDLE) {
        ACTIVE
    } else {
        IDLE
    }

    val state = transition(
        definition = starTransitionDefinition,
        initState = buttonState.value,
        toState = toState
    )

    StarButton(
        modifier = modifier,
        buttonState = buttonState,
        state = state,
        onToggle = onToggle
    )
}

@Composable
private fun StarButton(modifier: Modifier,
               buttonState: MutableState<StarAnimationDefinition.StarButtonState>,
               state: TransitionState,
               onToggle: () -> Unit){
    if (buttonState.value == ACTIVE) {
        Image(
            modifier = Modifier
                .height(state[starSize])
                .width(state[starSize])
                .clickable(
                    onClick = onToggle,
                    indication = null
                ),
            imageVector = Icons.Default.Star,
            colorFilter = ColorFilter(state[starColor],BlendMode.SrcIn),
            alignment = Alignment.Center
        )
    } else {
        Image(
            modifier = Modifier
                .height(state[starSize])
                .width(state[starSize])
                .clickable(
                    onClick = onToggle,
                    indication = null
                ),
            imageVector = Icons.Default.Star,
            colorFilter = ColorFilter(state[starColor],BlendMode.SrcIn),
            alignment = Alignment.Center,
        )
    }
}

object StarAnimationDefinition {
    enum class StarButtonState {
        IDLE, ACTIVE
    }

    val starColor = ColorPropKey(label = "starColor")
    val starSize = DpPropKey(label= "starDp")

    private val idleIconSize = 50.dp
    private val expandedIconSize = 80.dp

    @SuppressLint("Range")
    val starTransitionDefinition = transitionDefinition<StarAnimationDefinition.StarButtonState> {
        state(IDLE) {
            this[starColor] = Color.LightGray
            this[starSize] = idleIconSize
        }

        state(ACTIVE) {
            this[starColor] = Color.Blue
            this[starSize] = idleIconSize
        }

        transition(IDLE to ACTIVE) {
            starColor using tween(durationMillis = 500)

            starSize using keyframes {
                durationMillis = 500
                expandedIconSize at 100
                idleIconSize at 200
            }
        }

        transition(ACTIVE to IDLE) {
            starColor using tween(durationMillis = 500)

            starSize using keyframes {
                durationMillis = 500
                expandedIconSize at 100
                idleIconSize at 200
            }
        }
    }
}