package com.example.midterm1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BullEyeGame()
        }
    }
}

@Composable
fun BullEyeGame() {
    var target by remember { mutableStateOf(generateRandomValue()) }
    var minRange by remember { mutableStateOf(1) }
    var maxRange by remember { mutableStateOf(100) }
    var sliderValue by remember { mutableStateOf((minRange + maxRange) / 2) }
    var playerGuess by remember { mutableStateOf((minRange + maxRange) / 2) }
    var score by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("Your Score: $score") }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bull's Eye Game", fontSize = 24.sp)
        Text(text = "Move the slider as close as you can to: $sliderValue", fontSize = 16.sp)
        

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = { newValue ->
                sliderValue = newValue.toInt()
                playerGuess = newValue.toInt()
            },
            valueRange = minRange.toFloat()..maxRange.toFloat()
        )


        Button(
            onClick = {
                target = generateRandomValue()
                score += calculateScore(target, playerGuess)
                message = "Your Score: $score"
                sliderValue = (minRange + maxRange) / 2
//                val context = LocalContext.current
//                showToast(context, score)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Hit Me!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 20.sp,
            color = Color.Green
        )
    }
}

fun calculateScore(targetValue: Int, playerGuess: Int): Int {
    val difference = kotlin.math.abs(targetValue - playerGuess.toInt())
    return when {
        difference <= 3 -> 5
        difference <= 8 -> 1
        else -> 0
    }
}


fun generateRandomValue(): Int {
    return Random.nextInt(0, 101)
}


fun showToast(context: Context, score: Int) {
    val message = when (score) {
        5 -> "You are very close! You've been awarded 5 points."
        1 -> "You are close! You've been awarded 1 point."
        else -> "You missed this time. No points awarded."
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BullEyeGame()
}